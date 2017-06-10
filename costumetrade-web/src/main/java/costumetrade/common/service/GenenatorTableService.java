package costumetrade.common.service;

import com.mysql.jdbc.StringUtils;

import costumetrade.common.ColumnInfoVo;
import costumetrade.common.TableInfoVo;
import costumetrade.common.util.TableInfoUtils;

public class GenenatorTableService {

    public static void main(String[] args) throws Exception{
    	TableInfoVo tableInfoVo = TableInfoUtils.getTableInfoVo("config_product_back");
    	generatorSQL("config_product_back",tableInfoVo,"1");
    }

    public static String generatorSQL(String tableName,TableInfoVo tableInfoVo,String storeId) {
    	
        StringBuffer sb = new StringBuffer();
        try {
          
           
            sb.append(""); 
            createTableSql(sb,tableName,tableInfoVo,storeId);
            sb.append("");

            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {

        }
        return sb.toString();
    }

    private static void createTableSql(StringBuffer sb,String tableName,TableInfoVo tableInfoVo,String storeId){
			sb.append("\t\tcreate table `"+tableName+"_"+storeId+"` (\n");
			int i=0;
	   		for(ColumnInfoVo columnInfoVo:tableInfoVo.getColumnVoList()){
	   			if(tableInfoVo.getPrimaryKey().equalsIgnoreCase(columnInfoVo.getColumnName())){
	   				sb.append("\t\t\t"+columnInfoVo.getColumnName().toUpperCase() +" "+columnInfoVo.getColumnType()+"("+columnInfoVo.getLength()+") NOT NULL  auto_increment,");
	   				i++;
	   				continue;
	   			}
	   				sb.append("\t\t\t"+columnInfoVo.getColumnName().toUpperCase() +" "+columnInfoVo.getColumnType()+"("+columnInfoVo.getLength()+"),");
				    i++;
	   		}
			sb.append("\t\t primary key ("+tableInfoVo.getPrimaryKey()+"))"); 
			System.out.println("===="+sb.toString());	
    }
}