/**
 * Copyright 2016-2017 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.specification.kafka;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.ScriptProperty;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

/**
 * This test runs test cases which also work when run directly against a real Kafka broker.
 * Unignore the ignored methods to run against a real Kafka broker,  which can be started using one
 * of the docker stack config files in src/test/docker.
 */
public class TcpIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("scripts", "org/reaktivity/specification/kafka.tcp");

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${scripts}/describe.configs.v0/tcp.topic.with.log.compaction/client",
        "${scripts}/describe.configs.v0/tcp.topic.with.log.compaction/server"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestConfigForTopicWithLogCompaction() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/describe.configs.v0/tcp.topic.with.no.log.compaction/client",
        "${scripts}/describe.configs.v0/tcp.topic.with.no.log.compaction/server"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestConfigForTopicWithoutLogCompaction() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/metadata.v5/tcp.one.topic.multiple.partitions/client",
        "${scripts}/metadata.v5/tcp.one.topic.multiple.partitions/server"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestMetadataForOneTopicMultiplePartitionsSingleNode() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/metadata.v5/tcp.one.topic.multiple.nodes/client",
        "${scripts}/metadata.v5/tcp.one.topic.multiple.nodes/server"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestMetadataForOneTopicMultipleNodes() throws Exception
    {
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/fetch.v5/tcp.zero.offset.messages.multiple.partitions/client",
        "${scripts}/fetch.v5/tcp.zero.offset.messages.multiple.partitions/server"
    })
    @ScriptProperty("networkConnect \"tcp://localhost:9092\"")
    public void shouldFetchDataForOneTopicMultiplePartitions() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/fetch.v5/tcp.zero.offset.no.messages.multiple.partitions/client",
        "${scripts}/fetch.v5/tcp.zero.offset.no.messages.multiple.partitions/server"
    })
    @ScriptProperty({"networkConnect \"tcp://localhost:9092\"",
                     "networkAccept \"tcp://localhost:9092\""
    })
    public void shouldFetchNoDataForOneTopicMultiplePartitions() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/describe.configs.v0/tcp.topic.with.log.compaction/client"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestConfigForTopicWithLogCompactionBroker() throws Exception
    {
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/describe.configs.v0/tcp.topic.with.no.log.compaction/client"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestConfigForTopoutLogCompactionBroker() throws Exception
    {
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/metadata.v5/tcp.one.topic.multiple.partitions/client"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestMetadataForOneTopicMultiplePartitionsSingleNodeBroker() throws Exception
    {
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/metadata.v5/tcp.one.topic.multiple.nodes/client"
    })
    @ScriptProperty("brokerUrl \"tcp://localhost:9092\"")
    public void shouldRequestMetadataForOneTopicMultipleNodesBroker() throws Exception
    {
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/fetch.v5/tcp.zero.offset.messages.multiple.partitions/client"
    })
    @ScriptProperty("networkConnect \"tcp://localhost:9092\"")
    public void shouldFetchDataForOneTopicMultiplePartitionsBroker() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    // @Ignore
    @Specification({
        "${scripts}/fetch.v5/tcp.zero.offset.no.messages.multiple.partitions/client"
    })
    @ScriptProperty("networkConnect \"tcp://localhost:9092\"")
    public void shouldFetchNoDataForOneTopicMultiplePartitionsBroker() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

}
