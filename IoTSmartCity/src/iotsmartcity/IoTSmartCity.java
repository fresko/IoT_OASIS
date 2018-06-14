/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsmartcity;

import java.text.DecimalFormat;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import oracle.iot.client.DeviceModel;
import oracle.iot.client.device.DirectlyConnectedDevice;
import oracle.iot.client.device.VirtualDevice;

/**
 *
 * @author jcpaz
 */
public class IoTSmartCity {

    // private static final String REST_URI 
    private static final String REST_URI_NOISE
            = "http://192.168.2.1:8080/unidad/ORA-00006?variable=nivelRuido&sensor=11180300006";
    private static final String REST_URI_CO2
            = "http://192.168.2.1:8080/unidad/ORA-00001?variable=dioxidoCarbono&sensor=03021400001";
     private static final String REST_URI_NO2
            = "http://192.168.2.1:8080/unidad/ORA-00005?variable=dioxidoNitrogeno&sensor=03131400005";
    public Client client = ClientBuilder.newClient();

    // The URN marked with number 2.
    private static final String URN = "urn:com:oracle:iot:device:noise_sensor";
    private static final String URN_CO2 = "urn:com:oracle:iot:device:co2_sensor";
     private static final String URN_NO2 = "urn:com:oracle:iot:device:no2_sensor";


    // The URN marked with number 2.
    // private static final String URN = "urn:test:helloworld";
    // The attribute name marked with number 3.
    private static final String GREETING_ATTRIBUTE = "noise";
    private static final String GREETING_ATTRIBUTE_CO2 = "co2";
    private static final String GREETING_ATTRIBUTE_NO2 = "no2";

 
    public static void main(String[] args) throws Exception {

        IoTSmartCity iot = new IoTSmartCity();

        ////////////////////////CO2
        // The configuration is defined by the provisioning file.args[0]
        DirectlyConnectedDevice dcd = new DirectlyConnectedDevice("CO2_Sensor", "Sc_dem00");
        // Activate the device if it not activated (so that you can run the code more than once!)
        if (!dcd.isActivated()) {
            dcd.activate(URN_CO2);
        }
        // Set up a virtual device based on your device model
        DeviceModel dcdModel = dcd.getDeviceModel(URN_CO2);
        VirtualDevice virtualDevice = dcd.createVirtualDevice(dcd.getEndpointId(), dcdModel);
        //for(int i=0; i <= 110; i++){
        //Triggers a message to the Cloud Service.
        virtualDevice.set(GREETING_ATTRIBUTE_CO2, Double.parseDouble(iot.getJsonDevice(REST_URI_CO2).getValor()));
        // Thread.sleep(4000);
        // }
        dcd.close();

        /////////////////////////NOISE
        // The configuration is defined by the provisioning file.args[0]
        DirectlyConnectedDevice dcd2 = new DirectlyConnectedDevice("3", "Sc_dem00");
        // Activate the device if it not activated (so that you can run the code more than once!)
        if (!dcd2.isActivated()) {
            dcd2.activate(URN);
        }
        // Set up a virtual device based on your device model
        DeviceModel dcdModel2 = dcd2.getDeviceModel(URN);
        VirtualDevice virtualDevice2 = dcd2.createVirtualDevice(dcd2.getEndpointId(), dcdModel2);
        //for(int i=0; i <= 110; i++){
        //Triggers a message to the Cloud Service.
        virtualDevice2.set(GREETING_ATTRIBUTE, Double.parseDouble(iot.getJsonDevice(REST_URI_NOISE).getValor()));
        // Thread.sleep(4000);
        // }
        dcd2.close();
        
        
         ////////////////////////NO2
        // The configuration is defined by the provisioning file.args[0]
        DirectlyConnectedDevice dcd3 = new DirectlyConnectedDevice("NO2_Sensor", "Sc_dem00");
        // Activate the device if it not activated (so that you can run the code more than once!)
        if (!dcd3.isActivated()) {
            dcd3.activate(URN_NO2);
        }
        // Set up a virtual device based on your device model
        DeviceModel dcdModel3 = dcd3.getDeviceModel(URN_NO2);
        VirtualDevice virtualDevice3 = dcd3.createVirtualDevice(dcd3.getEndpointId(), dcdModel3);
        //for(int i=0; i <= 110; i++){
        //Triggers a message to the Cloud Service.
        virtualDevice3.set(GREETING_ATTRIBUTE_NO2, Double.parseDouble(iot.getJsonDevice(REST_URI_NO2).getValor()));
        // Thread.sleep(4000);
        // }
        dcd3.close();
        

    }

    public DeviceTest getJsonDevice(String REST_URI) {
        DeviceTest nd = client.target(REST_URI).path("").request(MediaType.APPLICATION_JSON).get(DeviceTest.class);
        return nd;
    }

    public Response createJsonEmployee(DeviceTest emp) {
        return client
                .target(REST_URI_NOISE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(emp, MediaType.APPLICATION_JSON));
    }

}
