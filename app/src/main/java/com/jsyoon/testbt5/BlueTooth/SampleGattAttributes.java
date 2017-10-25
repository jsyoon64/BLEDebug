package com.jsyoon.testbt5.BlueTooth;
/*
 * Copyright (C) 2013 The Android Open Source Project
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


import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();

    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String SLEEPSENSE_SERVICE = "0783b03e-8535-b5a0-7140-a304d2495cb7";
    public static String SLEEPSENSE_MEASUREMENT = "0783b03e-8535-b5a0-7140-a304d2495cb8";
    public static String SLEEPSENSE_CONTROL_NORESP = "0783b03e-8535-b5a0-7140-a304d2495cba";
    public static String SLEEPSENSE_CONTROL_RESP = "0783b03e-8535-b5a0-7140-a304d2495cb9";

    static {
        // Sample Services.
        attributes.put("00001800-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put(SLEEPSENSE_SERVICE, "SleepSense Service");
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");

        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put(SLEEPSENSE_MEASUREMENT, "SleepSense Measurement");
        attributes.put(SLEEPSENSE_CONTROL_NORESP, "SleepSense Ctr with Noresp");
        attributes.put(SLEEPSENSE_CONTROL_RESP, "SleepSense Ctr with Resp");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}