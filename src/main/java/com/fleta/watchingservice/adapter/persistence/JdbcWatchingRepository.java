package com.fleta.watchingservice.adapter.persistence;

import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JdbcWatchingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<WatchingDto1> nxvod211TobeSql00901(String cHjdongNo) {
        String sql = "SELECT /* nxvod211_TOBE_SQL009_01 */                                                                    " +
                "'cirtspt://'||A.stb_play_ip1||'/' as stbPlayIp1,                                                             " +
                "	   A.cdn_local_typ,                                                                                       " +
                "	   CASE WHEN A.ipv6_play_ip1 is not null AND A.ipv6_play_ip2 is not null AND A.ipv6_play_ip3 is not null  " +
                "	        THEN 'cirtspt://'||A.ipv6_play_ip1||'/'                                                           " +
                "		    ELSE ''                                                                                           " +
                "	   END ipv6_play_ip                                                                                       " +
                "FROM IMCSUSER.PT_LV_NODE_INFO A                                                                              " +
                "inner join IMCSUSER.PT_LV_DONG_INFO B                                                                        " +
                "        on A.sub_node_cd = B.sub_node_cd                                                                     " +
                "WHERE 1=1                                                                                                    " +
                "AND A.cdn_policy = '1'                                                                                       " +
                "AND B.dong_cd = trim(:cHjdongNo)";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("cHjdongNo", cHjdongNo);

            JpaResultMapper resultMapper = new JpaResultMapper();
            List<WatchingDto1> searchResult = resultMapper.list(query, WatchingDto1.class);
            return searchResult;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<WatchingDto2> nxvod211TobeSql01801(String cSaId, int pIdxSa) {
        String sql = "SELECT /* nxvod211_TOBE_SQL018_01 */  \n" +
                "       A.adi_album_id,                     \n" +
                "       A.watch_date                        \n" +
                "FROM VODUSER.PT_VO_SET_TIME_PTT A          \n" +
                "WHERE A.sa_id = :cSaId                     \n" +
                "AND A.p_idx_sa = :pIdxSa                   \n" +
                "AND A.nscn_cust_no = 'M'";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("cSaId", cSaId);
            query.setParameter("pIdxSa", pIdxSa);

            JpaResultMapper resultMapper = new JpaResultMapper();
            List<WatchingDto2> searchResult = resultMapper.list(query, WatchingDto2.class);
            return searchResult;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
