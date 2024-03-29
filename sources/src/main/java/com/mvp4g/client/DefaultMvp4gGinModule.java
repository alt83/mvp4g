/*
 * Copyright 2010 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.client;

import com.google.gwt.inject.client.AbstractGinModule;

/**
 * GIN module that will be used by the framework if not GIN module is specified.<br/>
 * <br/>
 * By default does nothing. 
 * 
 * 
 * @author plcoirier
 *
 */
public class DefaultMvp4gGinModule extends AbstractGinModule {

	@Override
	protected void configure() {				
	}

}
