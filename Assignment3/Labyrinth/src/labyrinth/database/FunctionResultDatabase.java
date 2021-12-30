/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andreicristea
 * @param <DataType>
 */
public interface FunctionResultDatabase<DataType> {
    DataType createData(ResultSet rs) throws SQLException;
}
