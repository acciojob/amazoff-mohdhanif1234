package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderHashMap = new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
    HashMap<String, List<String>> partnerOrderHashMap = new HashMap<>();
    List<String> assignedOrders = new ArrayList<>();
    public void addOrder(Order order) {
        String id = order.getId();
        orderHashMap.put(id, order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerHashMap.put(partnerId, deliveryPartner);
    }

    public void addOrderPartnerPair(String Oid,String Pid) {
        List<String> l = new ArrayList<>();
        if (!assignedOrders.contains(Oid)) {
            assignedOrders.add(Oid);

            if (partnerOrderHashMap.containsKey(Pid)) {
                l = partnerOrderHashMap.get(Pid);
                if (l.contains(Oid))
                    return;
                else {
                    l.add(Oid);

                }
            } else {
                l.add(Oid);
                partnerOrderHashMap.put(Pid, l);
            }
        }
    }

    public Order getOrderById(String orderId) {
        Order order = orderHashMap.get(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        List<String> listOrders = partnerOrderHashMap.get(partnerId);
        return listOrders.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> listOrders = partnerOrderHashMap.get(partnerId);
        return listOrders;
    }

    public List<String> getAllOrders() {
        List<String> l = new ArrayList<>();
        for (String s : partnerOrderHashMap.keySet()) {
            for (String p : partnerOrderHashMap.get(s)) {
                if (!l.contains(p)) {
                    l.add(p);
                }
            }
        }
        for (String s : orderHashMap.keySet()) {
            if (!l.contains(s)) {
                l.add(s);
            }
        }
        return l;
    }

    public Integer getCountOfUnassignedOrders() {
        List<String> l = new ArrayList<>();
        for (String s : orderHashMap.keySet()) {
            if (l.contains(s)) {
                l.add(s);
            }
        }
        int size = l.size();
        int count = 0;
        for (String s : partnerOrderHashMap.keySet()) {
            for (String p : partnerOrderHashMap.get(s)) {
                if (l.contains(p)) {
                    count++;
                }
            }
        }
        return size - count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return 0;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return null;
    }
    public void deletePartnerById(String Pid){
        List<String > l = new ArrayList<>();
        if(partnerOrderHashMap.containsKey(Pid))
            l = partnerOrderHashMap.get(Pid);
        partnerOrderHashMap.remove(Pid);
        for(int i=0;i<assignedOrders.size();i++){
            String s = assignedOrders.get(i);
            if(l.contains(s))
                assignedOrders.remove(i);
        }
    }
    public void deleteOrderById(String id){
        List<String> l = new ArrayList<>();
        if(orderHashMap.containsKey(id))
            orderHashMap.remove(id);
        for(String s: partnerOrderHashMap.keySet()){
            l = partnerOrderHashMap.get(s);
            if(l.contains(id)){
                for(int i=0;i<l.size();i++){
                    if(l.get(i).equals(id)){
                        l.remove(i);

                    }
                }
                partnerOrderHashMap.put(s,l);
                break;
            }

        }
        if (assignedOrders.contains(id)) {

            for(int i=0;i<assignedOrders.size();i++){
                if(assignedOrders.get(i).equals(id)){
                    assignedOrders.remove(i);
                }
            }
        }
    }
}
