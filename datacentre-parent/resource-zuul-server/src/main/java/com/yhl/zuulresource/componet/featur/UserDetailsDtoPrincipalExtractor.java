package com.yhl.zuulresource.componet.featur;


import com.yhl.authoritycommom.entity.OAthGrantedAuthority;
import com.yhl.zuulresource.entity.OAthUserDetailes;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// 这里定义你要携带的user信息
public class UserDetailsDtoPrincipalExtractor implements PrincipalExtractor {

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		UserDetails userDetails= new OAthUserDetailes();
		// todo 在这里设置你要携带的uer信息
		if (!Objects.isNull(map.get("authorities"))) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> roles = (List<Map<String, Object>>) map.get("authorities");
			List<OAthGrantedAuthority> authorities = roles.stream()
					.map(role -> new OAthGrantedAuthority(role.get("authority").toString(),
							Long.valueOf(role.get("id").toString())))
					.collect(Collectors.toList());
			// todo 在这里设置你要携带的uer信息
			//userDetails.setGrantedAuthority(authorities);
		}

		if (map.get("region") != null) {
			@SuppressWarnings({ "unchecked" })
			List<String> regions = (List<String>) map.get("region");
			//userDetails.setRegion(regions);
		}

		return userDetails;
	}

}
