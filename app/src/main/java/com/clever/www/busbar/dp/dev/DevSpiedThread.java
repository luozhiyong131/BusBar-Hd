package com.clever.www.busbar.dp.dev;

import com.clever.www.busbar.dp.data.hash.data.BoxDataHash;
import com.clever.www.busbar.dp.data.hash.data.BusDataHash;
import com.clever.www.busbar.dp.data.hash.data.BusHashTable;
import com.clever.www.busbar.dp.data.packages.DevDataPacket;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: lzy. Created on: 16-11-8.
 */

public class DevSpiedThread {
    private BusDataHash mHashData = BusHashTable.getHash();
    private static DevSpiedThread mDevSpid = null;

    public static DevSpiedThread get() {
        if(mDevSpid == null)
            mDevSpid = new DevSpiedThread();
        return mDevSpid;
    }


    private void delay(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void devAlarmStatus(DevDataPacket packet) {
        packet.curAlarm = packet.data.line.cur.crAlarm.maxData();
        packet.curAlarm += packet.data.line.cur.alarm.maxData();

        packet.volAlarm = packet.data.line.vol.crAlarm.maxData();
        packet.volAlarm += packet.data.line.vol.alarm.maxData();

        packet.envAlarm = packet.data.env.tem.crAlarm.maxData();
        packet.envAlarm += packet.data.env.tem.alarm.maxData();

        packet.status = packet.curAlarm + packet.volAlarm + packet.envAlarm;
    }

    private void checkDevState() {
        List<Integer> list = new ArrayList<>();
        mHashData.list(list);

        for(int i=0; i<list.size(); ++i) {
            BoxDataHash boxHash = mHashData.getDev(list.get(i));
            List<Integer> boxList = new ArrayList<>();
            boxHash.list(boxList);

            for (int j = boxList.size(); j > 0; --j) {
                DevDataPacket packet = boxHash.getPacket(boxList.get(j-1));
                if (packet.offLine > 0) {
                    packet.offLine--;
                    if (packet.offLine > 0) {
                        delay(255);
                        devAlarmStatus(packet);
                        if (packet.status > 0) { // 设备不正常

                        } else { // 设备工作正常

                        }

                    } else { // 设备离线
                    }
                } else {

                }
            }
        }
    }

    public void startThread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    delay(1350);
                    checkDevState();
                }
            }
        }.start();
    }
}
