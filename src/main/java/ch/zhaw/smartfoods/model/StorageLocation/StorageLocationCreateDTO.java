package ch.zhaw.smartfoods.model.StorageLocation;

import ch.zhaw.smartfoods.enums.StorageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class StorageLocationCreateDTO {
    
    private String name;
    private StorageType storageType;
    private String icon;
}
