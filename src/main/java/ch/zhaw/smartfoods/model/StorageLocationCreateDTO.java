package ch.zhaw.smartfoods.model;

import ch.zhaw.smartfoods.enums.StorageType;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class StorageLocationCreateDTO {
    
    private String name;
    private StorageType storageType;
    private String icon;
}
