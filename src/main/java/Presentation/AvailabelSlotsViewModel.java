package Presentation;

public class AvailabelSlotsViewModel {
    public int getAvialbaleSlots() {
        return AvialbaleSlots;
    }

    public void setAvialbaleSlots(int avialbaleSlots) {
        AvialbaleSlots = avialbaleSlots;
    }

    public int getSlotsBooked() {
        return SlotsBooked;
    }

    public void setSlotsBooked(int slotsBooked) {
        SlotsBooked = slotsBooked;
    }

    private int AvialbaleSlots;
    private int SlotsBooked;
}
