package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
  private final MemberService memberService;

  @GetMapping("/api/v1/members")
  public List<Member> membersV1(){
      List<Member> members = memberService.findAll();
      return  members;
  }

  @GetMapping("/api/v2/members")
  public Result memberV2(){
    List<Member> findMembers = memberService.findAll();
      List<MemberDto> members = findMembers.stream().map(m -> new MemberDto(m.getName())).collect(Collectors.toList());
      return new Result(members.size(),members);
  }
  @PostMapping("/api/v2/members")
    public CreateMemberResponse response(@RequestBody @Valid CreateMemberRequest request){
      Member member = new Member();
      member.setName(request.getName());
      memberService.join(member);
      return new CreateMemberResponse(member.getId());
  }
    @Data
    @AllArgsConstructor
  static class Result<T>{
      private int count;
      private T data;
  }
  @Data
  @AllArgsConstructor
  static class MemberDto{
      private String name;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class CreateMemberResponse{
      private Long id;
  }

  @PutMapping("/api/v2/members/{id}")
  public UpdateMemberResponse updateMember(@PathVariable("id")Long id, @RequestBody @Valid UpdateMemberRequest request){
      memberService.update(id, request.getName());
      Member member = memberService.findOne(id);
      return new UpdateMemberResponse(member.getId(), member.getName());
  }
  @Data
  @AllArgsConstructor
  static class  UpdateMemberResponse{
      private Long id;
      private String name;
  }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
  static class UpdateMemberRequest{
        private String name;

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
  static class  CreateMemberRequest{
      private String name;
  }

}
