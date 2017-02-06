package com.tenable.io.api;


import com.tenable.io.api.agentGroups.models.AgentGroup;
import com.tenable.io.api.agents.models.Agent;
import com.tenable.io.api.scanners.models.Scanner;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Copyright (c) 2017 Tenable Network Security, Inc.
 */
public class AgentGroupsApiClientTest extends TestBase {
    @Test
    public void TestAgentGroups() throws Exception {
        TenableIoClient apiClient = new TenableIoClient();
        String testName = "MyGroup_" + java.util.UUID.randomUUID().toString().substring( 0, 6 );
        String testName2 = "MyGroup_" + java.util.UUID.randomUUID().toString().substring( 0, 6 );

        List<Scanner> scanners = apiClient.getScannersApi().list();

        //create new agent group
        AgentGroup createdGroup = apiClient.getAgentGroupsApi().create( scanners.get( 0 ).getId(), testName );

        //list and verify group is created
        List<AgentGroup> groups = apiClient.getAgentGroupsApi().list( scanners.get( 0 ).getId() );
        boolean created = false;
        for( AgentGroup item : groups ) {
            if( item.getName().equals( testName ) ) {
                created = true;
            }
        }
        assertTrue( created );

        //test configure
        apiClient.getAgentGroupsApi().configure( scanners.get( 0 ).getId(), createdGroup.getId(), testName2 );

        //test details, verify name changed
        AgentGroup detail = apiClient.getAgentGroupsApi().details( scanners.get( 0 ).getId(), createdGroup.getId() );
        assertNotNull( detail );
        assertTrue( detail.getName().equals( testName2 ) );

        //add agent to group
        List<Agent> agents = apiClient.getAgentsApi().list( 1 );
        assertNotNull( agents );
        assertTrue( agents.size() > 0 );
        apiClient.getAgentGroupsApi().addAgent( 1, detail.getId(), agents.get( 1 ).getId() );

        //delete agent from group
        apiClient.getAgentGroupsApi().deleteAgent( 1, detail.getId(), agents.get( 1 ).getId() );

        //delete agent group
        apiClient.getAgentGroupsApi().delete( scanners.get( 0 ).getId(), createdGroup.getId() );

    }
}