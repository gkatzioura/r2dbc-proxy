/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.r2dbc.proxy.observation;

import io.micrometer.common.docs.KeyName;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;
import io.micrometer.observation.docs.ObservationDocumentation;

/**
 * @author Marcin Grzejszczak
 * @author Tadaya Tsuyukubo
 */
public enum R2DbcObservationDocumentation implements ObservationDocumentation {

    R2DBC_QUERY_OBSERVATION {
        @Override
        public Class<? extends ObservationConvention<? extends Observation.Context>> getDefaultConvention() {
            return R2dbcObservationConvention.class;
        }

        @Override
        public String getContextualName() {
            return "query";
        }

        @Override
        public KeyName[] getLowCardinalityKeyNames() {
            return LowCardinalityKeys.values();
        }

        @Override
        public KeyName[] getHighCardinalityKeyNames() {
            return HighCardinalityKeys.values();
        }

        @Override
        public Observation.Event[] getEvents() {
            return Events.values();
        }

        @Override
        public String getPrefix() {
            return "r2dbc.";
        }
    };

    enum Events implements Observation.Event {

        /**
         * Annotated before executing a method annotated with @ContinueSpan or @NewSpan.
         */
        QUERY_RESULT {
            @Override
            public String getName() {
                return "r2dbc.query_result";
            }
        }

    }

    enum LowCardinalityKeys implements KeyName {

        /**
         * Name of the R2DBC connection.
         */
        CONNECTION {
            @Override
            public String asString() {
                return "r2dbc.connection";
            }
        },

        /**
         * Name of the R2DBC thread.
         */
        THREAD {
            @Override
            public String asString() {
                return "r2dbc.thread";
            }
        }

    }

    enum HighCardinalityKeys implements KeyName {

        /**
         * Name of the R2DBC query.
         */
        QUERY {
            @Override
            public String asString() {
                return "r2dbc.query[%s]";
            }
        },

        /**
         * Query parameter values.
         */
        QUERY_PARAMETERS {
            @Override
            public String asString() {
                return "r2dbc.params[%s]";
            }
        }

    }

}
