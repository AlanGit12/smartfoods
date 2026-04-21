package ch.zhaw.smartfoods.model.StorageLocation;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.zhaw.smartfoods.enums.StorageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("StorageLocation")
public class StorageLocation {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private StorageType storageType;
    @NonNull
    private String icon;
    private String userId;
}
