package ru.podelochki.otus.homework4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

import com.sun.management.GarbageCollectionNotificationInfo;

public class MemoryLeak {

    public static final int ITERATIONS = 1500000;
    public static BufferedWriter[] writers =new BufferedWriter[5];
    public static long totalGcDuration = 0;
    public static int youngCounter = 0;
    public static int oldCounter = 0;
    public static long msYoungSpent = 0;
    public static long msOldSpent = 0;
    public static long curTime = new Date().getTime();
    public static int minute = 0;
    
    public static void main(String[] args) throws Exception {
    	
    	installGCMonitoring();
        List<Object> list = new ArrayList<>();
        System.out.println("started");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++) {
            add(list, 1000);
            
            remove(list, 990);
            if (i % 100000 == 0) {
            	System.out.println(i);
            	Thread.currentThread().sleep(Long.parseLong(args[0]));
            }
        }
        System.out.println("Finished after " + (System.currentTimeMillis() - startTime) + " ms.");
        for (int i = 0; i < writers.length; i++) {
        	if (writers[i] != null) {
        		writers[i].write(String.format("Last Minute from start. Young GC:%d times, for %d ms.\n", youngCounter, (msYoungSpent)));
        		
        		writers[i].write(String.format("Last Minute from start. Old GC:%d times, for %d ms.\n", oldCounter, (msOldSpent)));
        		writers[i].flush();
        		writers[i].close();
        	}
        }

    }

    public static void add(List<Object> list, int numElems) {
        for (int i = 0; i < numElems; i++) {
            list.add(new Object());
        }
    }

    public static void remove(List<Object> list, int numElems) {
        if (list.size() < numElems) return;
        int listSize = list.size();
        for (int i = listSize - 1; i > listSize - numElems - 1; i--) {
            list.remove(i);
        }
    }
    
    public static void installGCMonitoring(){

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        
        
        int i = 0;
        writers = new BufferedWriter[gcbeans.size()];
        for (GarbageCollectorMXBean gcbean : gcbeans) {
        	try {
				writers[i] = new BufferedWriter(new FileWriter(new File(gcbean.getName())));
				
			} catch (IOException e1) {

				e1.printStackTrace();
			}
          System.out.println(gcbean.getName());
         
          BufferedWriter tmpWriter = writers[i];
          i++;  
          NotificationEmitter emitter = (NotificationEmitter) gcbean;

          NotificationListener listener = new NotificationListener() {

            @Override
            public void handleNotification(Notification notification, Object handback) {

              if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {

                GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                long tmpDate = new Date().getTime();
                System.out.println(tmpDate - curTime);
                if ((tmpDate - curTime) > 60000) {
                    System.out.println(tmpWriter);
                	try {
                		minute++;
                		tmpWriter.write(String.format("%d Minute from start. Young GC:%d times, for %d ms.\n",minute, youngCounter, (msYoungSpent)));
                		
                		tmpWriter.write(String.format("%d Minute from start. Old GC:%d times, for %d ms.\n", minute, oldCounter, (msOldSpent)));
                		
                		tmpWriter.flush();
						youngCounter = 0;
			            oldCounter = 0;
			            msYoungSpent = 0;
			            msOldSpent = 0;
			            curTime= tmpDate; 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                
                long duration = info.getGcInfo().getDuration();
                String gctype = info.getGcAction();
                if ("end of minor GC".equals(gctype)) {
                	youngCounter++;
                	msYoungSpent += duration;
                } else if ("end of major GC".equals(gctype)) {
                  gctype = "Old Gen GC";
                  oldCounter++;
                  msOldSpent += duration;
                }
              }
            }
          };
          

          //Add the listener
          emitter.addNotificationListener(listener, null, null);
        
        
      }
    }
}
