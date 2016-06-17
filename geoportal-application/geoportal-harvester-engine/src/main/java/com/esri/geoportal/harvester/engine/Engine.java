/*
 * Copyright 2016 Esri, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.geoportal.harvester.engine;

import com.esri.geoportal.harvester.api.Processor;
import com.esri.geoportal.harvester.api.Trigger;
import com.esri.geoportal.harvester.api.defs.EntityDefinition;
import com.esri.geoportal.harvester.api.defs.Task;
import com.esri.geoportal.harvester.api.defs.TaskDefinition;
import com.esri.geoportal.harvester.api.defs.TriggerInstanceDefinition;
import com.esri.geoportal.harvester.api.defs.UITemplate;
import com.esri.geoportal.harvester.api.ex.DataProcessorException;
import com.esri.geoportal.harvester.api.ex.InvalidDefinitionException;
import com.esri.geoportal.harvester.engine.support.BrokerInfo;
import com.esri.geoportal.harvester.engine.support.ProcessReference;
import com.esri.geoportal.harvester.engine.support.TriggerReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Engine interface.
 */
public interface Engine {

  /**
   * Adds task definition.
   *
   * @param taskDefinition task definition
   * @return id of a new task
   * @throws DataProcessorException if accessing repository fails
   */
  UUID addTaskDefinition(TaskDefinition taskDefinition) throws DataProcessorException;

  /**
   * Creates a broker.
   *
   * @param brokerDefinition broker definition
   * @return broker info or <code>null</code> if broker has not been created
   * @throws DataProcessorException if accessing repository fails
   */
  BrokerInfo createBroker(EntityDefinition brokerDefinition) throws DataProcessorException;

  /**
   * Creates process.
   *
   * @param task task for the process
   * @return process handle
   * @throws InvalidDefinitionException if processor definition is invalid
   * @throws DataProcessorException if accessing repository fails
   */
  ProcessReference createProcess(Task task) throws InvalidDefinitionException, DataProcessorException;

  /**
   * Creates task to initialize.
   *
   * @param taskDefinition task definition
   * @return task
   * @throws InvalidDefinitionException if one of broker definitions appears to
   * be invalid
   */
  Task createTask(TaskDefinition taskDefinition) throws InvalidDefinitionException;

  /**
   * Deletes broker.
   *
   * @param brokerId broker id
   * @return <code>true</code> if broker has been deleted
   * @throws DataProcessorException if accessing repository fails
   */
  boolean deleteBroker(UUID brokerId) throws DataProcessorException;

  /**
   * Deletes task definition.
   *
   * @param taskId task id
   * @return <code>true</code> if task definition has been deleted
   * @throws DataProcessorException if accessing repository fails
   */
  boolean deleteTaskDefinition(UUID taskId) throws DataProcessorException;

  /**
   * Finds broker by id.
   *
   * @param brokerId broker id
   * @return broker info or <code>null</code> if no broker corresponding to the
   * broker id can be found
   * @throws DataProcessorException if accessing repository fails
   */
  BrokerInfo findBroker(UUID brokerId) throws DataProcessorException;

  /**
   * Gets broker definitions.
   *
   * @param category broker category
   * @return broker infos
   * @throws DataProcessorException if accessing repository fails
   */
  Collection<BrokerInfo> getBrokersDefinitions(BrokerInfo.Category category) throws DataProcessorException;

  /**
   * Gets inbound connector templates.
   *
   * @return collection of inbound connector templates
   */
  Collection<UITemplate> getInboundConnectorTemplates();

  /**
   * Gets outbound connector templates.
   *
   * @return collection of outbound connector templates
   */
  Collection<UITemplate> getOutboundConnectorTemplates();

  /**
   * Gets process by process id.
   *
   * @param processId process id.
   * @return process or <code>null</code> if no process available for the given
   * process id
   * @throws DataProcessorException if accessing repository fails
   */
  Processor.Process getProcess(UUID processId) throws DataProcessorException;

  /**
   * Lists all triggers.
   * @return list of triggers
   */
  List<Trigger> listTriggers();

  /**
   * Reads task definition.
   *
   * @param taskId task id
   * @return task definition
   * @throws DataProcessorException if accessing repository fails
   */
  TaskDefinition readTaskDefinition(UUID taskId) throws DataProcessorException;

  /**
   * Schedules task with trigger.
   * @param trigDef trigger instance definition
   * @return trigger reference
   * @throws InvalidDefinitionException if invalid definition
   * @throws DataProcessorException if error processing data
   */
  TriggerReference scheduleTask(TriggerInstanceDefinition trigDef) throws InvalidDefinitionException, DataProcessorException;
  
  /**
   * Deactivates trigger.
   * @param triggerInstanceUuid trigger uuid
   * @return trigger reference
   * @throws InvalidDefinitionException if invalid definition
   * @throws DataProcessorException if error processing data
   */
  TriggerReference deactivateTriggerInstance(UUID triggerInstanceUuid) throws InvalidDefinitionException, DataProcessorException;
  
  /**
   * Lists all activated triggers.
   * @return list of all activated triggers
   */
  List<TriggerReference> listActivatedTriggers();

  /**
   * Selects processes by predicate.
   *
   * @param predicate predicate
   * @return list of processes matching predicate
   * @throws DataProcessorException if accessing repository fails
   */
  List<Map.Entry<UUID, Processor.Process>> selectProcesses(Predicate<? super Map.Entry<UUID, Processor.Process>> predicate) throws DataProcessorException;

  /**
   * Selects task definitions.
   *
   * @param predicate predicate
   * @return list of task definitions matching predicate
   * @throws DataProcessorException if accessing repository fails
   */
  List<Map.Entry<UUID, TaskDefinition>> selectTaskDefinitions(Predicate<? super Map.Entry<UUID, TaskDefinition>> predicate) throws DataProcessorException;

  /**
   * Submits task definition.
   *
   * @param taskDefinition task definition
   * @return process handle
   * @throws InvalidDefinitionException invalid definition exception
   * @throws DataProcessorException if accessing repository fails
   */
  ProcessReference submitTaskDefinition(TaskDefinition taskDefinition) throws InvalidDefinitionException, DataProcessorException;

  /**
   * Creates a broker.
   *
   * @param brokerId broker id
   * @param brokerDefinition broker definition
   * @return broker info or <code>null</code> if broker has not been created
   * @throws DataProcessorException if accessing repository fails
   */
  BrokerInfo updateBroker(UUID brokerId, EntityDefinition brokerDefinition) throws DataProcessorException;

  /**
   * Updates task.
   *
   * @param taskId task id
   * @param taskDefinition task definition
   * @return old task definition or <code>null</code> if no old task
   * @throws DataProcessorException if accessing repository fails
   */
  TaskDefinition updateTaskDefinition(UUID taskId, TaskDefinition taskDefinition) throws DataProcessorException;
  
}
