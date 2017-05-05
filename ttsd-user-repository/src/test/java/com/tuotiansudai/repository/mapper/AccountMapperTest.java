package com.tuotiansudai.repository.mapper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tuotiansudai.repository.model.AccountModel;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.repository.model.UserStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class AccountMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void shouldCreateAccount() throws Exception {
        UserModel fakeUser = createFakeUser("testCreateAccount");

        AccountModel model = new AccountModel(fakeUser.getLoginName(), "payUserId", "payAccountId", new Date());

        accountMapper.create(model);

        AccountModel savedAccount = accountMapper.findByLoginName(fakeUser.getLoginName());

        assertNotNull(savedAccount);
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        UserModel fakeUser = createFakeUser("testUpdateAccount");

        AccountModel model = new AccountModel(fakeUser.getLoginName(), "payUserId", "payAccountId", new Date());

        accountMapper.create(model);

        model.setBalance(1);
        model.setFreeze(1);

        accountMapper.update(model);

        AccountModel updatedAccount = accountMapper.findByLoginName(fakeUser.getLoginName());

        assertThat(updatedAccount.getBalance(), is(1L));
        assertThat(updatedAccount.getFreeze(), is(1L));

    }

    @Test
    public void shouldFindByIdentityNumber() throws Exception {
        UserModel fakeUser = createFakeUser("testFindByIdentityNumber");
        fakeUser.setIdentityNumber(String.valueOf(new Random().nextInt(100)));
        userMapper.updateUser(fakeUser);
        assertNotNull(userMapper.findByIdentityNumber(fakeUser.getIdentityNumber()));
    }

    private UserModel createFakeUser(String loginName) {
        UserModel model = new UserModel();
        model.setLoginName(loginName);
        model.setPassword("password");
        model.setEmail("loginName@abc.com");
        model.setMobile(String.valueOf(new Random().nextInt(10000)));
        model.setUserName("userName");
        model.setIdentityNumber("identityNumber");
        model.setRegisterTime(new Date());
        model.setStatus(UserStatus.ACTIVE);
        model.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        userMapper.create(model);
        return model;
    }
}
