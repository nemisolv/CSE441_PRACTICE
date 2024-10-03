package com.nemisolv.b3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class StaffViewModel extends ViewModel {
    private final MutableLiveData<List<String>> staffDetails = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getStaffDetails() {
        return staffDetails;
    }

    public void addStaffDetails(String staffId, String name, String birthDate, String salary) {
        List<String> currentList = staffDetails.getValue();
        if (currentList != null) {
            String newDetails = staffId + "-" + name + "-" + birthDate + "-" + salary;
            currentList.add(newDetails);  // Thêm vào danh sách
            staffDetails.setValue(currentList);  // Cập nhật LiveData
        }
    }
}

