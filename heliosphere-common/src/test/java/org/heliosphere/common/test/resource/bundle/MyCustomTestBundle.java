/*
 * Copyright(c) 2016 - Heliosphere Corp.
 * ---------------------------------------------------------------------------
 * This file is part of the Heliosphere's project which is licensed under the 
 * Apache license version 2 and use is subject to license terms.
 * You should have received a copy of the license with the project's artifact
 * binaries and/or sources.
 * 
 * License can be consulted at http://www.apache.org/licenses/LICENSE-2.0
 * ---------------------------------------------------------------------------
 */
package org.heliosphere.common.test.resource.bundle;

import org.heliosphere.common.resource.bundle.IBundle;
import org.heliosphere.common.resource.bundle.ResourceBundleManager;
import org.heliosphere.common.resource.bundle.annotation.BundleEnumRegister;

/**
 * Test enumeration of the resource bundle externalized strings of the virtual <b>org.acme-base</b> module. 
 * Each enumerated value maps to a key in the corresponding resource bundle file.
 * <p>
 * <b>Note:</b><br>
 * In the Heliosphere's framework, the enumerated values are not in full uppercase.
 * <hr>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse - Heliosphere</a>
 * @version 1.0.0
 */
@SuppressWarnings("nls")
@BundleEnumRegister(priority = 50)
public enum MyCustomTestBundle implements IBundle
{
	/**
	 * Resource string file name.
	 * <p>
	 * <b>Rules:</b>:<br>
	 * It must be prefixed by <b>bundle.</b> and must not contain the language code nor the file extension such as: <b>_en.properties</b>
	 * <p>
	 * This enumerated value should always be created and must point to the name of the resource bundle file 
	 * containing the resource strings without the '_en' (language code) so that the {@link ResourceBundleManager} can
	 * discover, load and register the resource strings contained in the file associated with this enumeration class.
	 * <p>
	 * <b>Example</b>:<br>
	 * If the module's name is {@code foo.module-base}, then this enumerated value should have the value: {@code bundle.foo.module-base}
	 * <p>
	 * <b>Important</b>:<br>
	 * DO NOT DELETE THIS ENTRY!
	 */
	BundleFilename("bundle.example-module"),

	/**
	 * Root string path value.
	 * <p>
	 * <b>Example</b>:<br>
	 * If the resource strings start all with 'level1.level2', then this enumerated value should have the value: level1.level2.
	 * <p>
	 * <b>Important</b>:<br>
	 * DO NOT DELETE THIS ENTRY!
	 */
	BundleRoot("prefix1-prefix2."),

	/*
	 * ---------- Test.* ----------
	 */

	/**
	 * A dummy message from component: 'component name'.
	 */
	TestDummy("test.dummy");

	/**
	 * Resource bundle key.
	 */
	private final String key;

	/**
	 * Creates a new enumerated value based on the resource bundle key.
	 * <p>
	 * @param key Resource bundle key.
	 */
	private MyCustomTestBundle(final String key)
	{
		this.key = key;
	}

	@Override
	public final String getKey()
	{
		if (!key.equals(MyCustomTestBundle.BundleRoot.key) && !key.equals(MyCustomTestBundle.BundleFilename.key))
		{
			return MyCustomTestBundle.BundleRoot.getKey() + key;
		}

		return key;
	}

	@Override
	public final String getValue()
	{
		return ResourceBundleManager.getMessage(this);
	}
}
