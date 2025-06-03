package com.eatclub.service;

import com.eatclub.model.Deal;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MockRestaurantDataService implements RestaurantDataService {

    @Override
    public RestaurantData getRestaurantData() {
        RestaurantData data = new RestaurantData();
        data.setRestaurants(createMockRestaurants());
        return data;
    }

    private List<Restaurant> createMockRestaurants() {
        Restaurant masalaKitchen = new Restaurant();
        masalaKitchen.setObjectId("DEA567C5-F64C-3C03-FF00-E3B24909BE00");
        masalaKitchen.setName("Masala Kitchen");
        masalaKitchen.setAddress1("55 Walsh Street");
        masalaKitchen.setSuburb("Lower East");
        masalaKitchen.setOpen("3:00pm");
        masalaKitchen.setClose("9:00pm");
        
        Deal deal1 = new Deal();
        deal1.setObjectId("DEA567C5-0000-3C03-FF00-E3B24909BE00");
        deal1.setDiscount("50");
        deal1.setDineIn("false");
        deal1.setLightning("true");
        deal1.setOpen("3:00pm");
        deal1.setClose("9:00pm");
        deal1.setQtyLeft("5");
        
        Deal deal2 = new Deal();
        deal2.setObjectId("DEA567C5-1111-3C03-FF00-E3B24909BE00");
        deal2.setDiscount("40");
        deal2.setDineIn("true");
        deal2.setLightning("false");
        deal2.setQtyLeft("4");
        
        masalaKitchen.setDeals(Arrays.asList(deal1, deal2));

        Restaurant abcChicken = new Restaurant();
        abcChicken.setObjectId("D80263E8-FD89-2C70-FF6B-D854ADB8DB00");
        abcChicken.setName("ABC Chicken");
        abcChicken.setAddress1("361 Queen Street");
        abcChicken.setSuburb("Melbourne");
        abcChicken.setOpen("12:00pm");
        abcChicken.setClose("11:00pm");
        
        Deal deal3 = new Deal();
        deal3.setObjectId("D80263E8-0000-2C70-FF6B-D854ADB8DB00");
        deal3.setDiscount("30");
        deal3.setDineIn("false");
        deal3.setLightning("false");
        deal3.setQtyLeft("1");
        
        Deal deal4 = new Deal();
        deal4.setObjectId("D80263E8-1111-2C70-FF6B-D854ADB8DB00");
        deal4.setDiscount("20");
        deal4.setDineIn("true");
        deal4.setLightning("false");
        deal4.setQtyLeft("4");
        
        abcChicken.setDeals(Arrays.asList(deal3, deal4));

        Restaurant vrindavan = new Restaurant();
        vrindavan.setObjectId("CDB2B42A-248C-EE20-FF45-8D0A8057E200");
        vrindavan.setName("Vrindavan");
        vrindavan.setAddress1("261 Harris Street");
        vrindavan.setSuburb("Pyrmont");
        vrindavan.setOpen("6:00pm");
        vrindavan.setClose("9:00pm");
        
        Deal deal5 = new Deal();
        deal5.setObjectId("CDB2B42A-0000-EE20-FF45-8D0A8057E200");
        deal5.setDiscount("10");
        deal5.setDineIn("true");
        deal5.setLightning("true");
        deal5.setOpen("3:00pm");
        deal5.setClose("9:00pm");
        deal5.setQtyLeft("5");
        
        vrindavan.setDeals(Arrays.asList(deal5));

        Restaurant kekou = new Restaurant();
        kekou.setObjectId("B5713CD0-91BF-40C7-AFC3-7D46D26B00BF");
        kekou.setName("Kekou");
        kekou.setAddress1("396 Bridge Road");
        kekou.setSuburb("Richmond");
        kekou.setOpen("1:00pm");
        kekou.setClose("11:00pm");
        
        Deal deal6 = new Deal();
        deal6.setObjectId("B5713CD0-0000-40C7-AFC3-7D46D26B00BF");
        deal6.setDiscount("10");
        deal6.setDineIn("true");
        deal6.setLightning("true");
        deal6.setStart("2:00pm");
        deal6.setEnd("9:00pm");
        deal6.setQtyLeft("3");
        
        Deal deal7 = new Deal();
        deal7.setObjectId("B5713CD0-1111-40C7-AFC3-7D46D26B00BF");
        deal7.setDiscount("15");
        deal7.setDineIn("true");
        deal7.setLightning("true");
        deal7.setStart("5:00pm");
        deal7.setEnd("9:00pm");
        deal7.setQtyLeft("4");
        
        kekou.setDeals(Arrays.asList(deal6, deal7));

        Restaurant gyoza = new Restaurant();
        gyoza.setObjectId("21076F54-03E7-3115-FF09-75D07FFC7401");
        gyoza.setName("Gyoza Gyoza Melbourne Central");
        gyoza.setAddress1("211 La Trobe Street");
        gyoza.setSuburb("Melbourne");
        gyoza.setOpen("4:00pm");
        gyoza.setClose("10:00pm");
        
        Deal deal8 = new Deal();
        deal8.setObjectId("B5913CD0-0000-40C7-AFC3-7D46D26B01BF");
        deal8.setDiscount("25");
        deal8.setDineIn("true");
        deal8.setLightning("false");
        deal8.setQtyLeft("3");
        
        Deal deal9 = new Deal();
        deal9.setObjectId("B5713CD0-1111-40C7-AFC3-7D46D26B00BF");
        deal9.setDiscount("15");
        deal9.setDineIn("false");
        deal9.setLightning("false");
        deal9.setQtyLeft("4");
        
        gyoza.setDeals(Arrays.asList(deal8, deal9));

        Restaurant ozzyThai = new Restaurant();
        ozzyThai.setObjectId("178CC02C-69F5-5D90-FF67-84B135B19103");
        ozzyThai.setName("OzzyThai Cafe Bar ");
        ozzyThai.setAddress1("34 Saint Kilda Road");
        ozzyThai.setSuburb("Saint Kilda");
        ozzyThai.setOpen("8:00am");
        ozzyThai.setClose("3:00pm");
        
        Deal deal10 = new Deal();
        deal10.setObjectId("B5913CD0-0550-40C7-AFC3-7D46D26B01BF");
        deal10.setDiscount("30");
        deal10.setDineIn("false");
        deal10.setLightning("false");
        deal10.setQtyLeft("8");
        
        Deal deal11 = new Deal();
        deal11.setObjectId("B5713CD0-1361-40C7-AFC3-7D46D26B00BF");
        deal11.setDiscount("25");
        deal11.setDineIn("false");
        deal11.setLightning("false");
        deal11.setQtyLeft("7");
        
        ozzyThai.setDeals(Arrays.asList(deal10, deal11));

        return Arrays.asList(masalaKitchen, abcChicken, vrindavan, kekou, gyoza, ozzyThai);
    }
}