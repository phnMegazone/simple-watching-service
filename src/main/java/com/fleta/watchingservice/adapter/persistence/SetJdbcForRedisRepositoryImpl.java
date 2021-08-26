package com.fleta.watchingservice.adapter.persistence;

import com.fleta.watchingservice.domain.dto.Sql009_01Dto;
import com.fleta.watchingservice.domain.dto.Sql018_01Dto;
import com.fleta.watchingservice.port.SetJdbcForRedisRepository;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SetJdbcForRedisRepositoryImpl implements SetJdbcForRedisRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Sql009_01Dto> getSql009_01List() {
        String sql = "SELECT /* nxvod211_TOBE_SQL009_01 */\n" +
            "\tB.dong_cd,\n" +
            "\t'cirtspt://'||A.stb_play_ip1||'/' as stb_play_ip1,\n" +
            "\tA.cdn_local_typ,\n" +
            "\tCASE WHEN A.ipv6_play_ip1 is not null AND A.ipv6_play_ip2 is not null AND A.ipv6_play_ip3 is not null \n" +
            "\tTHEN 'cirtspt://'||A.ipv6_play_ip1||'/' \n" +
            "\tELSE '' \n" +
            "\tEND ipv6_play_ip\n" +
            "FROM\n" +
            "\tIMCSUSER.PT_LV_NODE_INFO A\n" +
            "INNER JOIN\n" +
            "\tIMCSUSER.PT_LV_DONG_INFO B\n" +
            "ON\n" +
            "\tA.sub_node_cd = B.sub_node_cd\n" +
            "WHERE\n" +
            "\t1=1\n" +
            "AND\n" +
            "\tA.cdn_policy = '1'";
        try {
            Query query = entityManager.createNativeQuery(sql);
            JpaResultMapper resultMapper = new JpaResultMapper();
            List<Sql009_01Dto> searchResult = resultMapper.list(query, Sql009_01Dto.class);
            return searchResult;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Sql018_01Dto> getSQL018_01List(int limit, int offset) {
        String sql = "SELECT A.sa_id, A.p_idx_sa, A.adi_album_id, A.watch_date\n" +
            "from VODUSER.PT_VO_SET_TIME_PTT A\n" +
            "where A.nscn_cust_no = 'M'\n" +
            "limit :limit offset :offset";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("limit", limit);
            query.setParameter("offset", offset);
            JpaResultMapper resultMapper = new JpaResultMapper();
            List<Sql018_01Dto> searchResult = resultMapper.list(query, Sql018_01Dto.class);
            return searchResult;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public int getSQL018_01Count() {
        String sql = "select COUNT(A.*) \n" +
            "from VODUSER.PT_VO_SET_TIME_PTT A \n" +
            "where A.nscn_cust_no = 'M'";
        try {
            Query query = entityManager.createNativeQuery(sql);
            return query.getFirstResult();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
