package com.example.smartShopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShoppingListRequest {
    private Long listId;
    private String newName;
    private String newAssignToUsername;
    private String newNote;
    private String newDate; // mm/dd/yyyy
}
