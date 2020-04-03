package processtest;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@Deployment(resources = {"httpConnector.bpmn"})
public class ProcessUnitTest {

    @Rule
    public ProcessEngineRule engine = new ProcessEngineRule();

    @Test
    public void happyPathTest() {
        ProcessInstance pi = runtimeService().startProcessInstanceByKey("HttpConnectorTestProcess", withVariables("userId", 1));
        assertThat(pi).isEnded().hasPassed("EmailFoundEndEvent").hasVariables("email", "userId");
    }

    @Test(expected = ProcessEngineException.class)
    public void failingPathTest() {
        runtimeService().startProcessInstanceByKey("HttpConnectorTestProcess", withVariables("userId", 100));
    }
}