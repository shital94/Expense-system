package com.revature.ers.middle;

import com.revature.ers.beans.ReimbursementType;
import com.revature.ers.data.DataFacade;

import java.util.List;


public class ReimbursementTypeService {
    public List<ReimbursementType> getReimbursementTypes() {
        return new DataFacade().getAllReimbursementTypes();
    }
}
