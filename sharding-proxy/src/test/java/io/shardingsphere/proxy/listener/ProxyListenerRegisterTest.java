/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
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
 * </p>
 */

package io.shardingsphere.proxy.listener;

import io.shardingsphere.proxy.config.RuleRegistry;
import io.shardingsphere.transaction.listener.local.LocalTransactionListener;
import io.shardingsphere.transaction.listener.xa.XATransactionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class ProxyListenerRegisterTest {
    
    private final ProxyListenerRegister proxyListenerRegister = new ProxyListenerRegister();
    
    @Mock
    private LocalTransactionListener localTransactionListener;
    
    @Mock
    private XATransactionListener xaTransactionListener;
    
    @Mock
    private RuleRegistry ruleRegistry;
    
    @Before
    public void setUp() throws ReflectiveOperationException {
        setField("localTransactionListener", localTransactionListener);
        setField("xaTransactionListener", xaTransactionListener);
        setField("ruleRegistry", ruleRegistry);
    }
    
    private void setField(final String fieldName, final Object fieldValue) throws ReflectiveOperationException {
        Field field = ProxyListenerRegister.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(proxyListenerRegister, fieldValue);
    }
    
    @Test
    public void assertRegister() {
        proxyListenerRegister.register();
        verify(localTransactionListener).register();
        verify(xaTransactionListener).register();
        verify(ruleRegistry).register();
    }
}
