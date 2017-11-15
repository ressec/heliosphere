package org.heliosphere.common.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.NotImplementedException;
import org.heliosphere.common.command.exception.CommandManagerException;
import org.heliosphere.common.command.internal.metadata.CommandCategory;
import org.heliosphere.common.command.internal.metadata.CommandDomain;
import org.heliosphere.common.command.internal.metadata.CommandGroup;
import org.heliosphere.common.command.internal.metadata.CommandMetadata;
import org.heliosphere.common.command.internal.metadata.CommandParameterMetadata;
import org.heliosphere.common.resource.ResourceException;
import org.heliosphere.common.resource.internal.Resource;

import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;

/**
 * A command manager to discover, register and access command definitions.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@Log4j
@UtilityClass
public class CommandManager
{
	/**
	 * Delimiter.
	 */
	@SuppressWarnings("nls")
	private final static String DELIMITER = ".";
	
	/**
	 * Properties file extension.
	 */
	@SuppressWarnings("nls")
	private final static String FILE_PROPERTIES = "properties";

	/**
	 * XML file extension.
	 */
	@SuppressWarnings("nls")
	private final static String FILE_XML = "xml";

	/**
	 * JSON file extension.
	 */
	@SuppressWarnings("nls")
	private final static String FILE_JSON = "JSON";

	/**
	 * Prefix of a command category.
	 */
	@SuppressWarnings("nls")
	private final static String COMMAND_CATEGORY_PREFIX = "metadata.category";

	/**
	 * Prefix of a command domain.
	 */
	@SuppressWarnings("nls")
	private final static String COMMAND_DOMAIN_PREFIX = "metadata.domain";

	/**
	 * Prefix of a command group.
	 */
	@SuppressWarnings("nls")
	private final static String COMMAND_GROUP_PREFIX = "metadata.group";

	/**
	 * Prefix of a command definition.
	 */
	@SuppressWarnings("nls")
	private final static String COMMAND_PREFIX = "metadata.command";

	/**
	 * Field: name.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_NAME = "name";

	/**
	 * Field: description.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_DESCRIPTION = "description";

	/**
	 * Field: prefix.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_PREFIX = "prefix";

	/**
	 * Field: example.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_EXAMPLE = "example";

	/**
	 * Field: processor.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_PROCESSOR = "processor";

	/**
	 * Field: parameter.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_PARAMETER = "parameter";

	/**
	 * Field: type.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_TYPE = "type";

	/**
	 * Field: syntax.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_SYNTAX = "syntax";

	/**
	 * Field: format.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_FORMAT = "format";

	/**
	 * Field: alias.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_ALIAS = "alias";

	/**
	 * Field: minimum.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_MINIMUM = "minimum";

	/**
	 * Field: maximum.
	 */
	@SuppressWarnings("nls")
	private final static String FIELD_MAXIMUM = "maximum";

	/**
	 * Properties file.
	 */
	private static PropertiesConfiguration properties = null;
	
	/**
	 * Command categories.
	 */
	private static Map<String, CommandCategory> CATEGORIES = new HashMap<>();

	/**
	 * Command domains.
	 */
	private static Map<String, CommandDomain> DOMAINS = new HashMap<>();

	/**
	 * Command groups.
	 */
	private static Map<String, CommandGroup> GROUPS = new HashMap<>();

	/**
	 * Commands.
	 */
	private static Map<String, CommandMetadata> COMMANDS = new HashMap<>();
	
	/**
	 * Internal ordered command structure.
	 */
	private static Map<String, Map<String, Map<String, Map<String, CommandMetadata>>>> MAP = new HashMap<>();

	/**
	 * Returns a list of registered command category names.
	 * <p>
	 * @return List of command category names.
	 */
	@SuppressWarnings("unchecked")
	public static final List<String> getCategoryNames()
	{
		//return Collections.unmodifiableList((List<CommandCategory>) CATEGORIES.values());
		return Collections.unmodifiableList((List<String>) MAP.keySet());
	}

	/**
	 * Returns a list of registered command domain names for a given command category name.
	 * <p>
	 * @param category Command category name.
	 * @return List of command domain names.
	 */
	@SuppressWarnings("unchecked")
	public static final List<String> getDomainNames(final @NonNull String category)
	{

		
		Map<String, Map<String, Map<String, CommandMetadata>>> MAP_DOMAINS = MAP.get(category);
		if (MAP_DOMAINS != null)
		{
			return Collections.unmodifiableList((List<String>) MAP_DOMAINS.keySet());
		}
		
		return null;
	}

	/**
	 * Returns a list of registered command group names for a given command category and domain name.
	 * <p>
	 * @param category Command category name.
	 * @param domain Command domain name.
	 * @return List of command group names.
	 */
	@SuppressWarnings("unchecked")
	public static final List<String> getGroupNames(final @NonNull String category, final @NonNull String domain)
	{
		Map<String, Map<String, Map<String, CommandMetadata>>> MAP_DOMAINS = MAP.get(category);
		if (MAP_DOMAINS != null)
		{
			Map<String, Map<String, CommandMetadata>> MAP_GROUPS = MAP_DOMAINS.get(domain);
			if (MAP_GROUPS != null)
			{
				return Collections.unmodifiableList((List<String>) MAP_GROUPS.keySet());
			}
		}
		
		return null;
	}

	/**
	 * Returns a list of registered command names for a given command category, domain and group names.
	 * <p>
	 * @param category Command category name.
	 * @param domain Command domain name.
	 * @param group Command group name.
	 * @return List of command group names.
	 */
	@SuppressWarnings("unchecked")
	public static final List<String> getCommandNames(final @NonNull String category, final @NonNull String domain, final @NonNull String group)
	{
		Map<String, Map<String, Map<String, CommandMetadata>>> MAP_DOMAINS = MAP.get(category);
		if (MAP_DOMAINS != null)
		{
			Map<String, Map<String, CommandMetadata>> MAP_GROUPS = MAP_DOMAINS.get(domain);
			if (MAP_GROUPS != null)
			{
				Map<String, CommandMetadata> MAP_COMMANDS = MAP_GROUPS.get(group);
				if (MAP_COMMANDS != null)
				{
					return Collections.unmodifiableList((List<String>) MAP_COMMANDS.keySet());
				}
			}
		}
		
		return null;
	}

	/**
	 * Returns the list of registered commands for a given command category, domain and group names.
	 * <p>
	 * @param category Command category name.
	 * @param domain Command domain name.
	 * @param group Command group name.
	 * @return List of commands.
	 */
	@SuppressWarnings("unused")
	public static final List<CommandMetadata> getCommands(final @NonNull String category, final @NonNull String domain, final @NonNull String group)
	{
		Map<String, Map<String, Map<String, CommandMetadata>>> MAP_DOMAINS = MAP.get(category);
		if (MAP_DOMAINS != null)
		{
			Map<String, Map<String, CommandMetadata>> MAP_GROUPS = MAP_DOMAINS.get(domain);
			if (MAP_GROUPS != null)
			{
				Map<String, CommandMetadata> MAP_COMMANDS = MAP_GROUPS.get(group);
				if (MAP_COMMANDS != null)
				{
					return Collections.unmodifiableList(new ArrayList<CommandMetadata>(MAP_COMMANDS.values()));
				}
			}
		}
		
		return null;
	}

	/**
	 * Returns the number of registered command definitions.
	 * <p>
	 * @return Number of registered command definitions.
	 */
	public static final int getCommandCount()
	{
		return COMMANDS.size();
	}
	
	/**
	 * Registers the command definitions declared in the given file.
	 * <p>
	 * @param filename Name of the file containing the command definitions to register.
	 * @throws CommandManagerException Thrown in case an error occurred while trying to register a command definition file.
	 */
	@SuppressWarnings("nls")
	public static final void registerFromFile(final @NonNull String filename) throws CommandManagerException
	{
		try
		{
			Resource file = new Resource(filename);
			String extension = FilenameUtils.getExtension(file.getFile().getName()); 
			
			if (extension.equalsIgnoreCase(FILE_PROPERTIES))
			{
				registerFromProperties(file);
			}
			else 
			{
				if (extension.equalsIgnoreCase(FILE_XML))
				{
					registerFromXML(file);
				}
				else 
				{
					if (extension.equalsIgnoreCase(FILE_JSON))
					{
						registerFromJSON(file);
					}
					else 
					{
						throw new CommandManagerException("Only properties, XML or JSON files are accepted!");
					}
				}
			}
		}
		catch (ResourceException e)
		{
			throw new CommandManagerException(e);
		}
	}
	
	/**
	 * Registers the command definitions declared in the given properties file.
	 * <p>
	 * @param file Resource representing the file containing the command definitions to register.
	 */
	private static final void registerFromProperties(final @NonNull Resource file)
	{
		try
		{
			properties = new PropertiesConfiguration(file.getFile().getAbsolutePath());
			properties.load();
			
			try
			{
				// Load the declared command categories.
				loadCategories();

				// Load the declared command domains.
				loadDomains();

				// Load the declared command groups.
				loadGroups();

				// Load the declared commands.
				loadCommands();
				
				// For all registered commands, load their parameters.
				loadCommandParameters();
				
				buildCommandTree();
			}
			catch (CommandManagerException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (ConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Registers the command definitions declared in the given XML file.
	 * <p>
	 * @param file Resource representing the file containing the command definitions to register.
	 */
	private static final void registerFromXML(final @NonNull Resource file)
	{
		throw new NotImplementedException();
	}

	/**
	 * Registers the command definitions declared in the given JSON file.
	 * <p>
	 * @param file Resource representing the file containing the command definitions to register.
	 */
	private static final void registerFromJSON(final @NonNull Resource file)
	{
		throw new NotImplementedException();
	}

	/**
	 * Load the command categories.
	 * <p>
	 * @throws CommandManagerException Thrown in case an error occurred with a command category definition.
	 */
	@SuppressWarnings("nls")
	private static final void loadCategories() throws CommandManagerException
	{
		CommandCategory category = null;
		String values[] = null;
		String tag = null;
		
		List<String> elements = Lists.newArrayList(properties.getKeys(COMMAND_CATEGORY_PREFIX));
		for (String element : elements)
		{
			values = element.split(Pattern.quote(DELIMITER));
			
			if (tag == null || !tag.equalsIgnoreCase(values[2]))
			{
				if (values[3].equalsIgnoreCase(FIELD_NAME))
				{
					tag = values[2];

					if (!CATEGORIES.containsKey(tag))
					{
						category = new CommandCategory(tag);
						CATEGORIES.put(tag, category);
					}
				}
			}
			else 
			{
				if (category == null)
				{
					throw new CommandManagerException(String.format("Category definition must begin with the 'name' entry for: %1s", element));
				}
				
				if (values[2].equalsIgnoreCase(tag))
				{
					if (values[3].equalsIgnoreCase(FIELD_DESCRIPTION))
					{
						category.setDescription(properties.getString(element));
					}
					else if (values[3].equalsIgnoreCase(FIELD_PREFIX))
					{
						category.setPrefix(properties.getString(element));
					}
					else 
					{
						throw new CommandManagerException(String.format("Invalid command category definition for: %1s", element));
					}
				}
			}
		}
		
		// Summarize the loaded categories.
		for (CommandCategory e : CATEGORIES.values())
		{
			log.info(String.format("Command category: %1s registered", e.toString()));
		}
	}

	/**
	 * Load the command domains.
	 * <p>
	 * @throws CommandManagerException Thrown in case an error occurred with a command domain definition.
	 */
	@SuppressWarnings("nls")
	private static final void loadDomains() throws CommandManagerException
	{
		CommandDomain domain = null;
		String values[] = null;
		String tag = null;
		
		List<String> elements = Lists.newArrayList(properties.getKeys(COMMAND_DOMAIN_PREFIX));
		for (String element : elements)
		{
			values = element.split(Pattern.quote(DELIMITER));
			
			if (tag == null || !tag.equalsIgnoreCase(values[2]))
			{
				if (values[3].equalsIgnoreCase(FIELD_NAME))
				{
					tag = values[2];

					if (!DOMAINS.containsKey(tag))
					{
						domain = new CommandDomain(tag);
						DOMAINS.put(tag, domain);
					}
				}
			}
			else 
			{
				if (domain == null)
				{
					throw new CommandManagerException(String.format("Domain definition must begin with the 'name' entry for: %1s", element));
				}
				
				if (values[2].equalsIgnoreCase(tag))
				{
					if (values[3].equalsIgnoreCase(FIELD_DESCRIPTION))
					{
						domain.setDescription(properties.getString(element));
					}
					else 
					{
						throw new CommandManagerException(String.format("Invalid command domain definition for: %1s", element));
					}
				}
			}
		}

		// Summarize the loaded domains.
		for (CommandDomain e : DOMAINS.values())
		{
			log.info(String.format("Command domain: %1s registered", e.toString()));
		}
	}

	/**
	 * Load the command groups.
	 * <p>
	 * @throws CommandManagerException Thrown in case an error occurred with a command group definition.
	 */
	@SuppressWarnings("nls")
	private static final void loadGroups() throws CommandManagerException
	{
		CommandGroup group = null;
		String values[] = null;
		String tag = null;
		
		List<String> elements = Lists.newArrayList(properties.getKeys(COMMAND_GROUP_PREFIX));
		for (String element : elements)
		{
			values = element.split(Pattern.quote(DELIMITER));
			
			if (tag == null || !tag.equalsIgnoreCase(values[3]))
			{
				if (values[3].equalsIgnoreCase(FIELD_NAME))
				{
					tag = values[2];

					if (!GROUPS.containsKey(tag))
					{
						group = new CommandGroup(tag);
						GROUPS.put(tag, group);
					}
				}
			}
			else 
			{
				if (group == null)
				{
					throw new CommandManagerException(String.format("Domain definition must begin with the 'name' entry for: %1s", element));
				}
				
				if (values[2].equalsIgnoreCase(tag))
				{
					if (values[3].equalsIgnoreCase(FIELD_DESCRIPTION))
					{
						group.setDescription(properties.getString(element));
					}
					else 
					{
						throw new CommandManagerException(String.format("Invalid command domain definition for: %1s", element));
					}
				}
			}
		}

		// Summarize the loaded groups.
		for (CommandGroup e : GROUPS.values())
		{
			log.info(String.format("Command group: %1s registered", e.toString()));
		}
	}

	/**
	 * LOad the commands.
	 * <p>
	 * @throws CommandManagerException Thrown in case an error occurred with a command definition.
	 */
	@SuppressWarnings("nls")
	private static final void loadCommands() throws CommandManagerException
	{
		CommandMetadata command = null;	
		String values[] = null;
		String category = null;
		String domain = null;
		String group = null;
		String tag = null;
		
		List<String> elements = Lists.newArrayList(properties.getKeys(COMMAND_PREFIX));
		for (String element : elements)
		{
			values = element.split(Pattern.quote(DELIMITER));

			// Check the command category exist
			if (values[2] != null)
			{
				category = values[2];
				if (!CATEGORIES.containsKey(category))
				{
					throw new CommandManagerException(String.format("Invalid entry for: %1s because the command category: %2s cannot be found!", element, category));
				}
			}

			// Check the command domain exist
			if (values[3] != null)
			{
				domain = values[3];
				if (!DOMAINS.containsKey(domain))
				{
					throw new CommandManagerException(String.format("Invalid entry for: %1s because the command domain: %2s cannot be found!", element, domain));
				}
			}

			// Check the command group exist
			if (values[4] != null)
			{
				group = values[4];
				if (!GROUPS.containsKey(group))
				{
					throw new CommandManagerException(String.format("Invalid entry for: %1s because the command group: %2s cannot be found!", element, group));
				}
			}
			
			if (tag == null || !tag.equalsIgnoreCase(values[5]))
			{
				// Check if the command name entry is present?
				if (values[6].equalsIgnoreCase(FIELD_NAME))
				{
					tag = values[5];

					if (!COMMANDS.containsKey(tag))
					{
						command = new CommandMetadata(category, domain, group, tag);
						COMMANDS.put(tag, command);
					}
				}
			}
			else 
			{
				if (command == null)
				{
					throw new CommandManagerException(String.format("Command definition must begin with the 'name' entry for: %1s", element));
				}

				// Check if the command description entry is present?
				if (values[6].equalsIgnoreCase(FIELD_DESCRIPTION) && values[5].equalsIgnoreCase(tag))
				{
					command.setDescription(properties.getString(element));
				}
				// Check if the command format entry is present?
				else if (values[6].equalsIgnoreCase(FIELD_FORMAT) && values[5].equalsIgnoreCase(tag))
				{
					command.setFormat(properties.getString(element));
				}
				// Check if the command alias entry is present?
				else if (values[6].equalsIgnoreCase(FIELD_ALIAS) && values[5].equalsIgnoreCase(tag))
				{
					command.addAlias(properties.getString(element));
				}
				// Check if the command processor entry is present?
				else if (values[6].equalsIgnoreCase(FIELD_PROCESSOR) && values[5].equalsIgnoreCase(tag))
				{
					command.setProcessor(properties.getString(element));
				}
				// Check if the command example entry is present?
				else if (values[6].equalsIgnoreCase(FIELD_EXAMPLE) && values[5].equalsIgnoreCase(tag))
				{
					command.addExample(properties.getString(element));
				}
				// Check if the command parameter entry is present?
				else if (values[6].equalsIgnoreCase(FIELD_PARAMETER) && values[5].equalsIgnoreCase(tag))
				{
					// Do not process those lines at this time! 
				}
				else 
				{
					throw new CommandManagerException(String.format("Invalid entry for: %1s!", element));
				}
			}
		}

		// Summarize the loaded commands.
		for (CommandMetadata e : COMMANDS.values())
		{
			log.info(String.format("Command: %1s registered", e.toString()));
		}
	}
	
	/**
	 * Load the command parameters.
	 * <p>
	 * @throws CommandManagerException Thrown in case an error occurred while loading a parameter definition.
	 */
	@SuppressWarnings("nls")
	private final static void loadCommandParameters() throws CommandManagerException
	{
		CommandParameterMetadata parameter = null;
		List<String> values = null;
		StringBuilder filter = null;
		String elements[] = null;
		String tag = null;
		
		List<CommandMetadata> commands = new ArrayList<>(COMMANDS.values());
		
		for (CommandMetadata command : commands)
		{
			tag = null;
			
			filter = new StringBuilder(COMMAND_PREFIX)
					.append(".")
					.append(command.getCategory())
					.append(".")
					.append(command.getDomain())
					.append(".")
					.append(command.getGroup())
					.append(".")
					.append(command.getName())
					.append(".parameter");
			
			values = Lists.newArrayList(properties.getKeys(filter.toString()));
			
			for (String entry : values)
			{
				elements = entry.split(Pattern.quote(DELIMITER));

				if (tag == null || !tag.equalsIgnoreCase(elements[7]))
				{
					if (elements[8].equalsIgnoreCase(FIELD_NAME))
					{
						tag = elements[7];
						
						parameter = new CommandParameterMetadata(properties.getString(entry));
						command.addParameter(tag, parameter);
					}
				}
				else 
				{
					if (parameter == null)
					{
						throw new CommandManagerException(String.format("Parameter definition must begin with the 'name' entry for: %1s", entry));
					}
					
					if (elements[8].equalsIgnoreCase(FIELD_DESCRIPTION))
					{
						parameter.setDescription(properties.getString(entry));
					}
					else if (elements[8].equalsIgnoreCase(FIELD_SYNTAX))
					{
						parameter.setSyntax(properties.getString(entry));
					}
					else if (elements[8].equalsIgnoreCase(FIELD_TYPE))
					{
						parameter.setType(properties.getString(entry));
					}
					else if (elements[8].equalsIgnoreCase(FIELD_MINIMUM))
					{
						parameter.setMinimum(properties.getString(entry));
					}
					else if (elements[8].equalsIgnoreCase(FIELD_MAXIMUM))
					{
						parameter.setMaximum(properties.getString(entry));
					}
					else if (elements[8].equalsIgnoreCase(FIELD_EXAMPLE))
					{
						parameter.addExample(properties.getString(entry));
					}
					else 
					{
						throw new CommandManagerException(String.format("Invalid command parameter property for: %1s!", entry));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("nls")
	private final static void buildCommandTree() throws CommandManagerException
	{
		CommandCategory c = null;
		CommandDomain d = null;
		CommandGroup g = null;

		Map<String, Map<String, Map<String, CommandMetadata>>> MAP_DOMAINS = null;
		Map<String, Map<String, CommandMetadata>> MAP_GROUPS = null;
		Map<String, CommandMetadata> MAP_COMMANDS = null;
		
		for (CommandMetadata command : new ArrayList<>(COMMANDS.values()))
		{
			c = CATEGORIES.get(command.getCategory());
			d = DOMAINS.get(command.getDomain());
			g = GROUPS.get(command.getGroup());
			
			if (c == null)
			{
				throw new CommandManagerException(String.format("Unknown command category for: %1s", c));
			}
			if (d == null)
			{
				throw new CommandManagerException(String.format("Unknown command domain for: %1s", d));
			}
			if (g == null)
			{
				throw new CommandManagerException(String.format("Unknown command group for: %1s", g));
			}

			MAP_DOMAINS = MAP.get(c.getName());
			if (MAP_DOMAINS == null)
			{
				MAP_DOMAINS = new HashMap<>();
			}

			MAP_GROUPS = MAP_DOMAINS.get(d.getName());
			if (MAP_GROUPS == null)
			{
				MAP_GROUPS = new HashMap<>();
			}

			MAP_COMMANDS = MAP_GROUPS.get(g.getName());
			if (MAP_COMMANDS == null)
			{
				MAP_COMMANDS = new HashMap<>();
			}

			MAP_COMMANDS.put(command.getName(), command);
			MAP_GROUPS.put(g.getName(), MAP_COMMANDS);
			MAP_DOMAINS.put(d.getName(), MAP_GROUPS);
			MAP.put(c.getName(), MAP_DOMAINS);
		}
	}
	
//	/**
//	 * Returns a list of commands being part of a given command category.
//	 * <p>
//	 * @param category Category name.
//	 * @return List of found commands.
//	 */
//	public final static List<CommandMetadata> getCommands(final @NonNull String category)
//	{
//		List<CommandMetadata> commands = new ArrayList<>();
//		CommandCategory c = null;
//		
//		for (CommandMetadata command : new ArrayList<>(COMMANDS.values()))
//		{
//			c = CATEGORIES.get(command.getCategory());
//			
//			if (c != null)
//			{
//				if (c.getName().equalsIgnoreCase(category))
//				{
//					commands.add(command);
//				}
//			}
//		}
//		
//		return Collections.unmodifiableList(commands);
//	}

//	/**
//	 * Returns a list of commands being part of a given category and domain.
//	 * <p>
//	 * @param category Category name.
//	 * @param domain Domain name.
//	 * @return List of found commands.
//	 */
//	public final static List<CommandMetadata> getCommands(final @NonNull String category, final @NonNull String domain)
//	{
//		List<CommandMetadata> commands = new ArrayList<>();
//		CommandCategory c = null;
//		CommandDomain d = null;
//		
//		for (CommandMetadata command : new ArrayList<>(COMMANDS.values()))
//		{
//			c = CATEGORIES.get(command.getCategory());
//			d = DOMAINS.get(command.getCategory());
//			
//			if (c != null && d != null)
//			{
//				if (c.getName().equalsIgnoreCase(category) && d.getName().equalsIgnoreCase(domain))
//				{
//					commands.add(command);
//				}
//			}
//		}
//		
//		return Collections.unmodifiableList(commands);
//	}

//	/**
//	 * Returns a list of commands being part of a given category, domain and group.
//	 * <p>
//	 * @param category Category name.
//	 * @param domain Domain name.
//	 * @param group Group name.
//	 * @return List of found commands.
//	 */
//	public final static List<CommandMetadata> getCommands(final @NonNull String category, final @NonNull String domain, final @NonNull String group)
//	{
//		List<CommandMetadata> commands = new ArrayList<>();
//		CommandCategory c = null;
//		CommandDomain d = null;
//		CommandGroup g = null;
//		
//		for (CommandMetadata command : new ArrayList<>(COMMANDS.values()))
//		{
//			c = CATEGORIES.get(command.getCategory());
//			d = DOMAINS.get(command.getCategory());
//			g = GROUPS.get(command.getCategory());
//			
//			if (c != null && d != null && g != null)
//			{
//				if (c.getName().equalsIgnoreCase(category) && d.getName().equalsIgnoreCase(domain) && g.getName().equalsIgnoreCase(group))
//				{
//					commands.add(command);
//				}
//			}
//		}
//		
//		return Collections.unmodifiableList(commands);
//	}
}