package module5.homeOrk6;

import module5.homeOrk1and2.Room;
import module5.homeOrk3.API;
import module5.homeOrk4.BookingComAPI;
import module5.homeOrk4.GoogleAPI;
import module5.homeOrk4.TripAdvisorAPI;
import module5.homeOrk5.DAOImpl;

public class Controller {
    API apis[] = new API[3];
   public DAOImpl dataBase = new DAOImpl();

    public Controller(){
        this.apis[0] = new BookingComAPI();
        this.apis[1] = new TripAdvisorAPI();
        this.apis[2] = new GoogleAPI();
    }

    public Room[] requstRooms(int price, int persons, String city, String hotel) {
        Room[] roomsRequested = new Room[0];
        API[] dbAPI = getApis();
        int searchCounter = 0;
        for (int i = 0; i < dbAPI.length; i++) {
            API api = dbAPI[i];
            Room[] roomsSearchAPIResult = api.findRooms(price, persons, city, hotel);
            for (int j = 0; j < roomsSearchAPIResult.length; j++) {
                if (roomsSearchAPIResult[j] != null) {
                    Room[] roomsTemp = new Room[searchCounter + 1];
                    for (int i1 = 0; i1 < roomsRequested.length; i1++) {
                        roomsTemp[i1] = roomsRequested[i1];
                    }
                    roomsTemp[searchCounter] = roomsSearchAPIResult[j];
                    roomsRequested = roomsTemp;
                    searchCounter++;
                }
            }
        }
        return roomsRequested;
    }

    public Room[] check(API api1, API api2) {
        Room[] roomsDB = new Room[0];
        Room[] roomsAPI1 = api1.getRooms();
        Room[] result;
        int searchCounter = 0;
        for (Room aRoomsAPI1 : roomsAPI1) {
            result = api2.findRooms(aRoomsAPI1.getPrice(), aRoomsAPI1.getPersons(), aRoomsAPI1.getCityName(), aRoomsAPI1.getHotelName());
            for (int i = 0; i < result.length; i++) {
                if (result[i] != null) {
                    Room[] roomsTemp = new Room[searchCounter + 1];
                    for (int i1 = 0; i1 < roomsDB.length; i1++) {
                        roomsTemp[i1] = roomsDB[i1];
                    }
                    roomsTemp[searchCounter] = result[i];
                    roomsDB = roomsTemp;
                    searchCounter++;
                }
            }
        }
        return roomsDB;
    }
    Room save(Room room) {

        return dataBase.save(room);
    }
    boolean delete(Room room) {

        return dataBase.delete(room);
    }
    Room update(Room room) {

        return dataBase.update(room);
    }
    Room findById(long id) {

        return dataBase.findById(id);
    }

    public API[] getApis() {
        return apis;
    }
}
