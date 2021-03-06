package xuanngoc.gardenwatersystem.controller.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xuanngoc.gardenwatersystem.model.Device;
import xuanngoc.gardenwatersystem.model.DeviceProperties;
import xuanngoc.gardenwatersystem.service.DeviceService;
import xuanngoc.gardenwatersystem.service.PlantWaterService;

import java.util.List;

@RestController
@RequestMapping("/api/device/")
public class RestDeviceController {

    private DeviceService deviceService;

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping("list")
    public List<DeviceProperties> deviceList() {
        return deviceService.findAll();
    }

    @GetMapping("{id}")
    public Device getDevice(@PathVariable Integer id) {
        return deviceService.getById(id);
    }

    @PostMapping("update/state/{deviceId}")
    public void updateState(@PathVariable Integer deviceId) {
        Device device = deviceService.getById(deviceId);
        device.setManual(!device.getManual());
        if (device.getManual()) {
            device.setStatus(PlantWaterService.WORKING_MANUAL);
        } else {
            device.setStatus(PlantWaterService.WORKING_AUTO);
        }
        device.setState(!device.getState());
        deviceService.saveOrUpdate(device);
    }

    @GetMapping("{id}/state")
    public boolean getDeviceState(@PathVariable Integer id) {
        return deviceService.getById(id).getState();
    }

    @GetMapping("{id}/status")
    public String getDeviceStatus(@PathVariable Integer id) {
        return deviceService.getById(id).getStatus();
    }



}
