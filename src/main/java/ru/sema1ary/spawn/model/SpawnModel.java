package ru.sema1ary.spawn.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sema1ary.spawn.dao.impl.SpawnDaoImpl;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "spawn", daoClass = SpawnDaoImpl.class)
public class SpawnModel {
    @DatabaseField(generatedId = true, unique = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private String location;
}
