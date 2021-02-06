import com.code.community.comment.model.po.Comment;
import com.code.community.comment.model.vo.CommentVo;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class test {
    @Test
    public void test(){
        Class<CommentVo> commentVoClass = CommentVo.class;

//        System.out.println(commentVoClass.getGenericInterfaces()[0]);
        //System.out.println(commentVoClass.getGenericSuperclass());

        ParameterizedType parameterizedType = (ParameterizedType) commentVoClass.getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //System.out.println(actualTypeArguments[0].getClass());
        //System.out.println(Comment.class.getTypeName());
        System.out.println(Comment.class == actualTypeArguments[0]);
    }
}
