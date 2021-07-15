package processtest;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;


public class ProcessUnitTest {

    @Rule
    public ProcessEngineRule engine = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"httpConnector.bpmn"})
    public void happyPathGETTest() {
        ProcessInstance pi = runtimeService().startProcessInstanceByKey("HttpConnectorTestProcess", withVariables("userId", 1));
        assertThat(pi).isEnded().hasPassed("EmailFoundEndEvent").hasVariables("email", "userId");
    }

    @Test(expected = ProcessEngineException.class)
    @Deployment(resources = {"httpConnector.bpmn"})
    public void failingPathTest() {
        runtimeService().startProcessInstanceByKey("HttpConnectorTestProcess", withVariables("userId", 100));
    }

    @Test
    @Deployment(resources = {"httpConnectorPOST.bpmn"})
    public void happyPathPOSTTest() {
        ProcessInstance pi = runtimeService().startProcessInstanceByKey("HttpConnectorTestPOSTProcess", withVariables("name", "Andy Serkis"));
        assertThat(pi).isEnded().hasPassed("UserCreatedEndEvent").hasVariables("response", "name");
    }
}