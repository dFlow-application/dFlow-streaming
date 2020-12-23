dFlow - streaming application

- roomId hashMap -> sessions
- routes -> /streaming/rooms/id -> hashMap (sessions)
- session -> + roomId



Controller Streaming:
- deleteRoom
- addRoom
- editRoom

Controller User:
- add user
- remove users
- get userById
- update user 

Socket:
- getUsersById(roomId) {tuplu id/value}


Need to check:

Socket:
- Socket new type for subscribers list (call via socket)

Thread (nTime) (need to check)

Database:

User:
userID -> Primary key
userRooms -> List<Rooms> {Rooms: roomID, roomKey??,
    roomType, roomOwner, roomCapacity, roomActualSize, roomStatus(Enum: Open, closed..) }
userData -> 

