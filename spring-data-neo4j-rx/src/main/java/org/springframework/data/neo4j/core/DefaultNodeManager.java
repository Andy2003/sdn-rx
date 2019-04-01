/*
 * Copyright (c) 2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.core;

import org.apiguardian.api.API;
import org.neo4j.driver.v1.StatementRunner;
import org.neo4j.driver.v1.Transaction;
import org.springframework.data.neo4j.core.context.DefaultPersistenceContext;
import org.springframework.data.neo4j.core.context.PersistenceContext;
import org.springframework.data.neo4j.core.schema.Schema;
import org.springframework.lang.Nullable;

/**
 * @author Michael J. Simons
 */
@API(status = API.Status.INTERNAL, since = "1.0")
class DefaultNodeManager implements NodeManager {

	private final PersistenceContext persistenceContext = new DefaultPersistenceContext();

	private final Schema schema;

	private final Neo4jTemplate neo4jTemplate;

	private final Transaction transaction;

	DefaultNodeManager(Schema schema, StatementRunner statementRunner) {
		this.schema = schema;
		this.neo4jTemplate = new Neo4jTemplate(() -> statementRunner);
		this.transaction = statementRunner instanceof Transaction ? (Transaction) statementRunner : null;
	}

	@Override
	@Nullable
	public Transaction getTransaction() {
		return transaction;
	}

	@Override
	public Object executeQuery(String query) {

		return neo4jTemplate.executeQuery(query);
	}
}