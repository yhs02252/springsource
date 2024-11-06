package com.example.mart.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entitty.cascade.Child;
import com.example.mart.entitty.cascade.Parent;
import com.example.mart.repository.cascade.ChildRepository;
import com.example.mart.repository.cascade.ParentRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CascadeRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void cascadeInsertTest() {
        Parent parent = Parent.builder()
                .name("부모3")
                .build();
        parentRepository.save(parent); // <- parent 정보를 먼저 입력하지 않는다면 child save는 작동하지
        // 않음

        LongStream.rangeClosed(1, 3).forEach(i -> {
            Child child = Child.builder()
                    .id(i)
                    .name("자식" + i)
                    .parent(parent)
                    .build();

            childRepository.save(child);
            // parent.getChilds().add(child); // child 정보는 DB에 추가되지 않음
        });

        // parentRepository.save(parent);
    }

    @Test
    public void cascadeTypePersist() {
        Parent parent = Parent.builder()
                // 1) [부모 id(SEQUENCE 생성)]와 자식 [자식 id(SEQUENCE 생성)] 둘다 시퀀스 생성인 경우
                // - 부모와 자식 둘다 생성됨(연동)

                // parent_seq.nextval => save 작동 시 함께 작동되니 직접 id를 기입하면 update로서 작동하거나 거부됨

                // .id(10L) // 2) DataIntegrityViolationException : <== ※ save가 insert가 아닌
                // update로
                // 작동하기에 객체를 찾지 못함
                // 부모 id를 직접입력 후 [자식id(SEQUENCE 생성)] 경우 -> 생성 자체가 되지 않음
                .name("부모11")
                .build();
        // parentRepository.save(parent);

        LongStream.rangeClosed(1, 3).forEach(i -> {
            Child child = Child.builder()
                    // .id(i) // 3) InvalidDataAccessApiUsageException => ※ save가 insert가 아닌 update로
                    // 작동하기에 객체를 찾지 못함
                    // [부모 id(SEQUENCE 생성)] 후 child를 직접 지정할 경우 -> 생성 자체가 되지 않음

                    // 4) 부모 id와 자식 id둘다 함께 직접입력할 경우 -> 부모는 생성이 되지만 자식은 생성되지 않음
                    .name("자식" + i)
                    .parent(parent)
                    .build();

            // childRepository.save(child);
            parent.getChilds().add(child);
        });

        parentRepository.save(parent);
    }

    @Test
    public void testChildRead() {
        // 자식2 정보 조회(+부모조회)
        Child child = childRepository.findById(3L).get();
        System.out.println(child);
        System.out.println(child.getParent());
    }

    @Test
    @Transactional
    public void testParentRead() {
        // 부모 정보 조회(+자식조회)
        Parent parent = parentRepository.findById(16L).get();
        System.out.println(parent);

        parent.getChilds().forEach(child -> {
            System.out.println(child);
        });
    }

    @Test
    public void deleteChildFromParent() {
        // 부모 삭제시 관련되어있는 자식 같이 삭제
        // 자식삭제 코드를 작성하지 않고
        parentRepository.deleteById(21L);
    }

    @Commit // 코드는 실행됐지만 delete가 작동되지않아 실행결과에 맞는 결과가 작동하도록 만듬
    @Test
    @Transactional
    public void testCascadeAllDeleteParent() {
        Parent parent = parentRepository.findById(18L).get();
        // 부모 삭제시 관련되어있는 자식 같이 삭제
        // 자식삭제 코드를 작성하지 않고

        // CASCADE.ALL 기준
        parent.getChilds().remove(0); // 여기서 0번 index의 자식은 부모를 잃고 고아객체가 되어버림
                                      // 이는 부모가 없으므로 orphanRemoval = true 에 의해 자동삭제됨
        // parent.getChilds().remove(1);
        // parent.getChilds().remove(2);
        parentRepository.save(parent);
    }
}
