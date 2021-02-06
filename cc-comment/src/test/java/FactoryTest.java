import com.code.community.comment.model.vo.CommentVo;
import com.code.community.common.factory.VoFactory;
import com.code.community.common.model.po.BasePo;
import org.junit.Test;

public class FactoryTest {
    @Test
    public void test(){
        VoFactory.createVo(CommentVo.class,new BasePo());

    }
}
