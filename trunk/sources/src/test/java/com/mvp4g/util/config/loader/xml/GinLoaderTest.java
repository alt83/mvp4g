package com.mvp4g.util.config.loader.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.GinModuleElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

public class GinLoaderTest extends AbstractMvp4gElementLoaderTest<GinModuleElement, GinModuleLoader> {

	@Test
	public void testEmptyElement() throws Mvp4gXmlException {
		assertEquals( 0, basicLoader.loadElements().size() );
		assertNull( basicLoader.loadElement() );
	}

	@Test
	public void testLoadOk() throws Mvp4gXmlException {
		List<String> attributes = convertToList( basicLoader.getRequiredAttributeNames() );
		attributes.addAll( convertToList( basicLoader.getOptionalAttributeNames() ) );
		List<String> multiValues = convertToList( basicLoader.getMultiValueAttributeNames() );
		multiValues.addAll( convertToList( basicLoader.getOptionalMultiValueAttributeNames() ) );
		List<String> parents = convertToList( basicLoader.getParentAttributeNames() );

		GinModuleLoader loader = newLoader( xmlBuilder.getConfigAttribute( attributes, multiValues, parents, 1, false, isSingleNode() ) );
		assertEquals( loader.loadElement(), new ArrayList<GinModuleElement>( loader.loadElements() ).get( 0 ) );

	}

	@Override
	protected String getTagName() {
		return "gin";
	}

	@Override
	protected boolean isSingleNode() {
		return true;
	}

	@Override
	protected GinModuleLoader newLoader( XMLConfiguration xml ) {
		return new GinModuleLoader( xml );
	}

}
