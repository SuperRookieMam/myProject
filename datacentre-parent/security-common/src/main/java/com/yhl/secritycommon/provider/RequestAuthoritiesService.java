package com.yhl.secritycommon.provider;

import com.yhl.secritycommon.access.RequestAuthorityAttribute;

import java.util.List;

public interface RequestAuthoritiesService {
    public List<RequestAuthorityAttribute> listAllAttributes();
}
