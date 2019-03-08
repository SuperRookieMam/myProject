package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "access_token",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"user_info","client_info"})}
)
public class AccessToken extends BaseEntity<String>  implements OAuth2AccessToken{
     private static final long serialVersionUID = -5620808551700545738L;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "user_info")
     private UserInfo userInfo;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "client_info")
     private ClientInfo clientInfo;

     @Column(name = "access_token")
     private  String accessToken;

     private Set<String> scopes;

     private  String tokenType = TOKEN_TYPE;

     // token有效时间
     @Column(name = "access_token_validity_seconds")
     private Long validitySeconds;

     @Column(name = "refresh_token_validity_seconds")
     private Long refreshTokenTime;
     /**
      * 附加信息
      * 令牌序列化器使用additionalInformation映射导出OAuth扩展使用的任何字段。
      * @从序列化令牌中的字段名返回映射到要导出的值。默认的序列化器
      *使用Jackson的Java对象自动JSON映射(用于令牌端点流)或隐式调用
      * . tostring()在“value”对象(用于隐式流)上，作为序列化过程的一部分。
      * */
     @Override
     public Map<String, Object> getAdditionalInformation() {
          Map<String,Object> map =new HashMap<>();
          return map;
     }

     @Override
     public Set<String> getScope() {
          return scopes;
     }

     @Override
     public OAuth2RefreshToken getRefreshToken() {//存储这个token的ID
          return new DefaultOAuth2RefreshToken(getId());
     }

     @Override
     public String getTokenType() {
          return tokenType;
     }

     @Override
     public boolean isExpired() {
          return refreshTokenTime+validitySeconds < System.currentTimeMillis();
     }

     @Override
     public Date getExpiration() {
          return new Date(refreshTokenTime+validitySeconds);
     }

     @Override
     public int getExpiresIn() {
          return (int)(validitySeconds/1000);
     }

     @Override
     public String getValue() {
          return null;
     }
}
