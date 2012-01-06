package com.lexicalscope.jewel.cli;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

public class UnexpectedTrailingValueException extends OptionValidationException {
    private static final long serialVersionUID = 4186655034725500358L;

    public UnexpectedTrailingValueException(final OptionSpecification specification, final String description) {
        super(specification, CliValidationException.m_messages.getString("validationError.UnexpectedTrailingValue"));
    }
}