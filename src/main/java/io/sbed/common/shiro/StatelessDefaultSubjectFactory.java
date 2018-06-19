package io.sbed.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.stereotype.Component;

/**
 * Description: 通过调用 context.setSessionCreationEnabled(false) 表示不创建会话；如果之后调用 Subject.getSession() 将抛出 DisabledSessionException 异常。 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/18 上午8:35
 */
@Component("subjectFactory")
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}