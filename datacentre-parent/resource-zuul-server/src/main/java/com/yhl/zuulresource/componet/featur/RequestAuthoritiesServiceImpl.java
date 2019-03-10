package com.yhl.zuulresource.componet.featur;

import com.yhl.yhlsecuritycommon.componet.access.RequestAuthorityAttribute;
import com.yhl.yhlsecuritycommon.componet.provider.RequestAuthoritiesService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RequestAuthoritiesServiceImpl implements RequestAuthoritiesService {
    @Override
    public List<RequestAuthorityAttribute> listAllAttributes() {
        return Collections.emptyList();
    }
}
