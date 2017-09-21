import com.collibra.dgc.core.api.dto.instance.asset.ChangeAssetRequest;
import com.collibra.dgc.core.api.dto.user.FindUsersRequest;
import com.collibra.dgc.core.api.dto.PagedResponse;
import com.collibra.dgc.core.api.model.instance.attribute.Attribute;
import com.collibra.dgc.core.api.dto.instance.asset.AddAssetRequest;
import com.collibra.dgc.core.api.dto.instance.asset.ChangeAssetRequest;
import com.collibra.dgc.core.api.dto.instance.asset.FindAssetsRequest;
import com.collibra.dgc.core.api.dto.instance.attachment.AddAttachmentRequest;
import com.collibra.dgc.core.api.dto.instance.attachment.AddAttachmentRequest.FileReference;
import com.collibra.dgc.core.api.dto.instance.comment.AddCommentRequest;
import com.collibra.dgc.core.api.dto.instance.community.FindCommunitiesRequest;
import com.collibra.dgc.core.api.dto.instance.responsibility.FindResponsibilitiesRequest;
import com.collibra.dgc.core.api.dto.role.FindRolesRequest;
import com.collibra.dgc.core.api.model.instance.Responsibility;
import com.collibra.dgc.core.api.model.reference.ResourceReference;
import com.collibra.dgc.core.api.dto.workflow.FindWorkflowInstancesRequest;
import com.collibra.dgc.core.api.dto.instance.attribute.FindAttributesRequest;
import com.collibra.dgc.core.api.dto.instance.attribute.ChangeAttributeRequest;
import com.collibra.dgc.core.api.dto.instance.attribute.AddAttributeRequest;
import com.collibra.dgc.core.api.model.instance.Attachment;
import com.collibra.dgc.core.api.dto.instance.attachment.FindAttachmentsRequest;
import com.collibra.dgc.core.dto.sync.MappingInfo;
import com.collibra.dgc.core.api.dto.instance.relation.AddRelationRequest;
import com.collibra.dgc.core.api.dto.instance.relation.ChangeRelationRequest;
import com.collibra.dgc.core.api.dto.instance.relation.FindRelationsRequest;
import com.collibra.dgc.core.api.dto.workflow.FindWorkflowTasksRequest;
import com.collibra.dgc.core.api.model.instance.Community;
import com.collibra.dgc.core.api.model.instance.Domain;
import com.collibra.dgc.core.api.dto.workflow.StartWorkflowInstancesRequest;
import com.collibra.dgc.core.api.dto.workflow.FindWorkflowDefinitionsRequest;
import com.collibra.dgc.core.api.dto.instance.complexrelation.FindComplexRelationsRequest;
import com.collibra.dgc.core.api.dto.instance.domain.AddDomainRequest;
import com.collibra.dgc.core.api.dto.instance.domain.FindDomainsRequest;
import com.collibra.dgc.core.api.dto.instance.responsibility.AddResponsibilityRequest;
import com.collibra.dgc.core.api.model.workflow.WorkflowInstance;
import com.collibra.dgc.core.api.model.workflow.task.WorkflowTask;
import com.collibra.dgc.core.api.model.workflow.WorkflowDefinition;
import com.collibra.dgc.core.api.model.workflow.WorkflowBusinessItemType;
import com.collibra.dgc.core.api.model.instance.Relation;
import com.collibra.dgc.core.api.model.user.User;
import com.collibra.dgc.core.api.model.role.Role;
import com.collibra.dgc.core.api.model.instance.Asset;
import com.collibra.dgc.core.api.model.instance.ComplexRelation;
import com.collibra.dgc.core.api.model.ResourceType;
import com.collibra.dgc.core.dto.search.SearchParameters

def kgiApplication_GetBaseURL() {
  return applicationApi.getInfo().getBaseUrl();
}

def kgiAsset_AddStr(String domainId, String name, String typeId) {
  return kgiAsset_Add(UUID.fromString(domainId), name, UUID.fromString(typeId));
}

def kgiAsset_Add(UUID domainId, String name, UUID typeId) {

  AddAssetRequest request = new AddAssetRequest();
  request.builder();

  request.setName(name);
  request.setTypeId(typeId);
  request.setDomainId(domainId);
  return assetApi.addAsset(request);
}

def kgiAsset_ChangeSignifierStr(String assetId, String name) {
  return kgiAsset_ChangeSignifier(UUID.fromString(assetId), UUID.fromString(name));
}

def kgiAsset_ChangeSignifier(UUID assetId, UUID name) {

  ChangeAssetRequest request = new ChangeAssetRequest();
  request.builder();

  request.setId(assetId);
  request.setName(name);

  return assetApi.changeAsset(request);

}

def kgiAsset_ChangeStatusStr(String assetId, String statusAssetId) {
  return kgiAsset_ChangeStatus(UUID.fromString(assetId), UUID.fromString(statusAssetId));
}

def kgiAsset_ChangeStatus(UUID assetId, UUID statusAssetId) {

  ChangeAssetRequest request = new ChangeAssetRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setStatusId(statusAssetId);

  return assetApi.changeAsset(changeAssetRequest);

}

def kgiAsset_FindContainingSignifierStr(String name, String domainId, String communityId, Collection<String> typeIdStrs, boolean typeInheritance, boolean excludeMeta, ResourceType resourceType, int offset, int limit) {

  List<UUID> typeIds = new ArrayList<UUID>();
  typeIdStrs.each { typeIdStr ->
    typeIds.add(UUID.fromString(typeIdStr));
  }

  return kgiAsset_FindContainingSignifier(
    name, UUID.fromString(domainId), UUID.fromString(communityId), typeIds, typeInheritance, excludeMeta, resourceType, offset, limit);

}

def kgiAsset_FindContainingSignifier(String name, UUID domainId, UUID communityId, Collection<UUID> typeIds, boolean typeInheritance, boolean excludeMeta, ResourceType resourceType, int offset, int limit) {

  FindAssetsRequest request = new FindAssetsRequest();
  request.setName(name);
  request.setDomainId(UUID.fromString(domainId));
  request.setCommunityId(communityId);
  request.setTypeIds(typeIds);
  request.setTypeInheritance(typeInheritance);
  request.setExcludeMeta(excludeMeta);

  PagedResponse<Asset> response = findAssets(request);

  return response.getResults();

}

def kgiAsset_GetStr(String assetId) {
  return assetApi.getAsset(UUID.fromString(assetId));
}

def kgiAsset_Get(UUID assetId) {
  return assetApi.getAsset(assetId);
}

def kgiAsset_RemoveStr(String assetId) {
  assetApi.removeAsset(UUID.fromString(assetId));
}

def kgiAsset_Remove(UUID assetId) {
  assetApi.removeAsset(assetId);
}

def kgiAttachment_AddAttachmentToResourceStr(String baseResourceId, ResourceType baseResourceType, String file, String fileName) {
  kgiAttachment_AddAttachmentToResource(UUID.fromString(baseResourceId), baseResourceType, file, fileName);
}

def kgiAttachment_AddAttachmentToResource(UUID baseResourceId, ResourceType baseResourceType, String file, String fileName) {

  AddAttachmentRequest request = new AddAttachmentRequest();
  request.builder();

  AddAttachmentRequest.FileReference reference = new AddAttachmentRequest.FileReference();
  reference.setName(fileName);
  request.setFile(reference);
  request.setBaseResourceId(baseResourceId);
  request.setBaseResourceType(baseResourceType);
  attachmentApi.addAttachment(request);

}

def kgiAttachment_FindByResourceStr(String baseResourceId) {
  return kgiAttachment_FindByResource(UUID.fromString(baseResourceId));
}

def kgiAttachment_FindByResource(UUID baseResourceId) {
  FindAttachmentsRequest request = new FindAttachmentsRequest();
  request.builder();
  request.setBaseResourceId(baseResourceId);
  PagedResponse<Attachment> response = attachmentApi.findAttachments(request);
  return response.getResults();
}

def kgiAttachment_RemoveStr(String attachmentId) {
  attachmentApi.removeAttachment(UUID.fromString(attachmentId));
}

def kgiAttachment_Remove(UUID attachmentId) {
  attachmentApi.removeAttachment(attachmentId);
}

def kgiAttribute_AddDate(UUID assetId, String typeId, Object value) {
  AddAttributeRequest request = new AddAttributeRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeId(UUID.fromString(typeId));
  request.setValue(value);
  return attributeApi.addAttribute(request);
}

def kgiAttribute_AddMultiValueList(UUID assetId, String typeId, List<String> values) {

  values.each { value->
    AddAttributeRequest request = new AddAttributeRequest();
    request.builder();
    request.setAssetId(assetId);
    request.setTypeId(UUID.fromString(typeId));
    request.setValue(value);
    attributeApi.addAttribute(request);
  }

}

def kgiAttribute_AddNumeric(UUID assetId, String typeId, Object value) {
  AddAttributeRequest request = new AddAttributeRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeId(UUID.fromString(typeId));
  request.setValue(value);
  return attributeApi.addAttribute(request);
}

def kgiAttribute_AddSingleValueList(UUID assetId, String typeId, Object value) {
  AddAttributeRequest request = new AddAttributeRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeId(UUID.fromString(typeId));
  request.setValue(value);
  return attributeApi.addAttribute(request);
}

def kgiAttribute_AddString(UUID assetId, String typeId, Object value) {
  AddAttributeRequest request = new AddAttributeRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeId(UUID.fromString(typeId));
  request.setValue(value);
  return attributeApi.addAttribute(request);
}

def kgiAttribute_Add(UUID assetId, String typeId, Object value) {
  return kgiAttribute_AddString(assetId, typeId, value);
}

def kgiAttribute_Upsert(String assetStrId, String attributeTypeStrId, Object val){
  
  UUID assetId = UUID.fromString(assetStrId);
  UUID attributeTypeId = UUID.fromString(attributeTypeStrId);

  Object value = val;
  
  if(val != null && val.getClass() == com.collibra.dgc.core.workflow.activiti.extensions.formtype.TextArea) {
    value = val.toString();
  }

  FindAttributesRequest request = new FindAttributesRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeIds(Arrays.asList(attributeTypeId));

  PagedResponse<Attribute> priorValues = attributeApi.findAttributes(request);
  if(priorValues.total > 0) {
    UUID_PriorAttr = priorValues.getResults().get(0).getId();
    ChangeAttributeRequest changeRequest = new ChangeAttributeRequest();
    changeRequest.builder();
    changeRequest.setId(attributeTypeId);
    changeRequest.setValue(value);
    attributeApi.changeAttribute(changeRequest);
  } else {
    AddAttributeRequest addRequest = new AddAttributeRequest();
    addRequest.builder();
    addRequest.setAssetId(assetId);
    addRequest.setTypeId(attributeTypeId);
    addRequest.setValue(value);
    attributeApi.addAttribute(addRequest);
  }

}

def kgiAttribute_ChangeDateStr(String attributeId, Object newValue) {
  kgiAttribute_ChangeDate(UUID.fromString(attributeId), newValue);
}

def kgiAttribute_ChangeDate(UUID attributeId, Object newValue) {
  ChangeAttributeRequest request = new ChangeAttributeRequest();
  request.builder();
  request.setId(attributeId);
  request.setValue(newValue); 
  attributeApi.changeAttribute(request);
}

def kgiAttribute_ChangeSingleValueListStr(String attributeId, Object newValue) {
  kgiAttribute_ChangeSingleValueList(UUID.fromString(attributeId), newValue);
}

def kgiAttribute_ChangeSingleValueList(UUID attributeId, String newValue) {
  ChangeAttributeRequest request = new ChangeAttributeRequest();
  request.builder();
  request.setId(attributeId);
  request.setValue(newValue); 
  attributeApi.changeAttribute(request);
}

def kgiAttribute_ChangeStringStr(String attributeId, Object newValue) {
  kgiAttribute_ChangeString(UUID.fromString(attributeId), newValue);
}

def kgiAttribute_ChangeString(UUID attributeId, String newValue) {
  ChangeAttributeRequest request = new ChangeAttributeRequest();
  request.builder();
  request.setId(attributeId);
  request.setValue(newValue); 
  attributeApi.changeAttribute(request);
}

def kgiAttribute_GetAttributesOfType(UUID assetId, String attributeTypeId) {

  FindAttributesRequest request = new FindAttributesRequest();
  request.builder();

  request.setAssetId(assetId);
  List typeIds = new ArrayList();
  typeIds.add(UUID.fromString(attributeTypeId)); 
  request.setTypeIds(typeIds);

  PagedResponse<Attribute> response = attributeApi.findAttributes(request);
  return response.getResults();

}

def kgiAttribute_GetDescriptionsForAssetStr(String assetId) {
  return kgiAttribute_GetDescriptionsForAsset(UUID.fromString(assetId));
}

def kgiAttribute_GetDescriptionsForAsset(UUID assetId) {

  FindAttributesRequest request = new FindAttributesRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeIds(Arrays.asList("00000000-0000-0000-0000-000000003114"));

  String description = null;

  PagedResponse<Attribute> response = attributeApi.findAttributes(request);
  if(response.getTotal() > 0) {
    description = response.getResults().get(0).getValue();
  }

  return description;

}

def kgiAttribute_GetDateStr(String attributeId) {
  return attributeApi.getAttribute(UUID.fromString(attributeId)).getValue();
}

def kgiAttribute_GetDate(UUID attributeId) {
  return attributeApi.getAttribute(attributeId).getValue();
}

def kgiAttribute_GetStr(String attributeId) {
  return attributeApi.getAttribute(UUID.fromString(attributeId));
}

def kgiAttribute_Get(UUID attributeId) {
  return attributeApi.getAttribute(attributeId);
}

def kgiAttribute_GetNumericStr(String attributeId) {
  return attributeApi.getAttribute(UUID.fromString(attributeId)).getValue();
}

def kgiAttribute_GetNumeric(UUID attributeId) {
  return attributeApi.getAttribute(attributeId).getValue();
}

def kgiAttribute_GetStringStr(String attributeId) {
  return attributeApi.getAttribute(UUID.fromString(attributeId)).getValue();
}

def kgiAttribute_GetString(UUID attributeId) {
  return attributeApi.getAttribute(attributeId).getValue();
}

def kgiAttribute_RemoveStr(String attributeId) {
  return attributeApi.removeAttribute(UUID.fromString(attributeId));
}

def kgiAttribute_Remove(UUID attributeId) {
  return attributeApi.removeAttribute(attributeId);
}

def kgiComment_AddStr(String baseResourceId, ResourceType baseResourceType, String content) {
  kgiComment_Add(UUID.fromString(baseResourceId), baseResourceType, content);
}

def kgiComment_Add(UUID baseResourceId, ResourceType baseResourceType, String content) {
 
  AddCommentRequest request = new AddCommentRequest();
  request.builder();
  request.setBaseResourceId(baseResourceId);
  request.setBaseResourceType(baseResourceType);
  request.setContent(content);
  commentApi.addComment(request);

}

def kgiCommunity_GetByName(String name) {

  Community community = null;
  FindCommunitiesRequest request = new FindCommunitiesRequest();
  request.builder();
  request.setName(name);
  PagedResponse<Community> communities = communityApi.findCommunities(request);
  if (communities > 0) {
    community = communities.getResults().get(0);
  }

  return community;

}

def kgiCommunity_GetByUri(String uri) {

  // not sure this can be implemented 

}

def kgiComplexRelation_Change(String complexRelation, Map<String,List <String>> relations, Map<String,List <?>> attributes) {

}

def kgiComplexRelation_GetComplexRelationshipStr(String complexRelationId) {
  return complexRelationApi.getComplexRelation(UUID.fromString(complexRelationId));
}

def kgiComplexRelation_GetComplexRelationship(UUID complexRelationId) {
  return complexRelationApi.getComplexRelation(complexRelationId);
}

def kgiComplexRelations_GetComplexRelationStr(String complexRelationId) {
  return complexRelationApi.getComplexRelation(UUID.fromString(complexRelationId));
}

def kgiComplexRelations_GetComplexRelation(UUID complexRelationId) {
  return complexRelationApi.getComplexRelation(complexRelationId);
}

def kgiComplexRelations_GetComplexRelationsStr(String assetId, String typeId) {
  return kgiComplexRelations_GetComplexRelations(UUID.fromString(assetId), typeId);
}

def kgiComplexRelations_GetComplexRelations(UUID assetId, String typeId) {

  FindComplexRelationsRequest request = new FindComplexRelationsRequest();
  request.builder();
  request.setAssetId(assetId);
  request.setTypeId(UUID.fromString(typeId));
  PagedResponse<ComplexRelation> response = findComplexRelations(request);

  response.getResults();

}

def kgiGetJSONDataTable(String tableViewConfig, int sEcho) {

}
def kgiIssue_Add(String responsibleCommunity, String signifier, String type, String description, String status, String priority, List <String> relatedTerms, List <String> relationTypes, List <java.lang.Boolean> directions, List <String> classifications, String analysis, String resolution, String requester, List <String> assignees, List <String> reviewer) {

}

def kgiIssue_GetIssues(com.collibra.dgc.core.dto.issue.IssueFilter filter) {

}
/* */

def kgiLogInfo(String message) {
  loggerApi.info(message);
}

def kgiMapping_Get(String mappingId, Integer offset, Integer limit) {
    return mappingComponent.getMappings(mappingId ,offset , limit);
}

def kgiMapping_Add(MappingInfo mappingInfo) {

}

def kgiMapping_Remove(String mapping) {

}

def kgiRelation_Add(String sourceId, String typeId, String targetId, Long startDate, Long endDate) {
  kgiRelation_Add(UUID.fromString(sourceId), UUID.fromString(typeId), UUID.fromString(targetId), startDate, endDate);
}

def kgiRelation_Add(UUID sourceId, UUID typeId, UUID targetId, Long startDate, Long endDate) {

  AddRelationRequest request = new AddRelationRequest();
  request.builder();
  request.setSourceId(sourceId);
  request.setTargetId(targetId);
  request.setTypeId(UUID.fromString(typeId));
  request.setStartingDate(startingDate);
  request.setEndingDate(endingDate);

  return relationApi.addRelation(request);

}

def kgiRelation_ChangeStr(String relationId, String sourceId, String targetId, Long startDate, Long endDate, String status) {
  kgiRelation_Change(UUID.fromString(relationId), UUID.fromString(sourceId), UUID.fromString(targetId), startDate, endDate, status);
}

def kgiRelation_Change(UUID relationId, UUID sourceId, UUID targetId, Long startDate, Long endDate, String status) {

  // status is ignored by the API anyway so don't pass it along!!!

  ChangeRelationRequest request = new ChangeRelationRequest();
  request.builder();
  request.setId(relationId);
  request.setSourceId(sourceId);
  request.setTargetId(targetId);
//  request.setTypeId(UUID.fromString(typeId));
  request.setStartingDate(startingDate);
  request.setEndingDate(endingDate);

  relationApi.addRelation(request);

}

def kgiRelation_ChangeStatus(UUID assetId, String status) {
  // this is an undocumented API call not in the V.1 API.

}

def kgiRelation_FindBySourceAndTargetAndTypeStr(String relationTypeId, String sourceId, String targetId, int offset, int limit) {
  return kgiRelation_FindBySourceAndTargetAndType(
      UUID.fromString(relationTypeId), UUID.fromString(sourceId), UUID.fromString(targetId), offset, limit);
}

def kgiRelation_FindBySourceAndTargetAndType(UUID relationTypeId, UUID sourceId, UUID targetId, int offset, int limit) {


  FindRelationsRequest request = new FindRelationsRequest();
  request.builder();
  request.setSourceId(sourceId);
  request.setTargetId(targetId);
  request.setRelationTypeId(relationTypeId)

  PagedResponse<Relation> response = relationApi.findRelations(request);
  response.setLimit(limit);
  response.setOffset(offset);

  return response.getResults(); 
  
}

def kgiRelation_FindBySourceAndTypeStr(String relationTypeId, String sourceId, int offset, int limit) {
  return kgiRelation_FindBySourceAndType(UUID.fromString(relationTypeId),  UUID.fromString(sourceId), offset, limit);
}

def kgiRelation_FindBySourceAndType(UUID relationTypeId, UUID sourceId, int offset, int limit) {

  FindRelationsRequest request = new FindRelationsRequest();
  request.builder();
  request.setSourceId(sourceId);
  request.setRelationTypeId(relationTypeId)

  PagedResponse<Relation> response = relationApi.findRelations(request);
  response.setLimit(limit);
  response.setOffset(offset);

  return response.getResults(); 

}

def kgiRelation_FindByTargetAndTypeStr(String relationTypeId, String targetId, int offset, int limit) {
  return kgiRelation_FindByTargetAndType(UUID.fromString(relationTypeId), UUID.fromString(targetId), offset, limit);
}

def kgiRelation_FindByTargetAndType(UUID relationTypeId, UUID targetId, int offset, int limit) {

  FindRelationsRequest request = new FindRelationsRequest();
  request.builder();
  request.setTargetId(targetId);
  request.setRelationTypeId(relationTypeId)

  PagedResponse<Relation> response = relationApi.findRelations(request);
  response.setLimit(limit);
  response.setOffset(offset);

  return response.getResults(); 

}

def kgiRelation_FindBySourceStr(String sourceId, int offset, int limit) {
  return kgiRelation_FindBySource(UUID.fromString(sourceId), offset, limit);
}

def kgiRelation_FindBySource(UUID sourceId, int offset, int limit) {

  FindRelationsRequest request = new FindRelationsRequest();
  request.builder();
  request.setSourceId(sourceId);

  PagedResponse<Relation> response = relationApi.findRelations(request);
  response.setLimit(limit);
  response.setOffset(offset);

  return response.getResults(); 

}

def kgiRelation_RemoveStr(String relationId) {
  relationApi.removeRelation(UUID.fromString(relationId));
}

def kgiRelation_Remove(UUID relationId) {
  relationApi.removeRelation(relationId);
}

def kgiRelations_HasBySourceAndTypeStr(String relationTypeId, String sourceId) {
  return kgiRelations_HasBySourceAndType(UUID.fromString(relationTypeId), UUID.fromString(sourceId));
}

def kgiRelations_HasBySourceAndType(UUID relationTypeId, UUID sourceId) {

  FindRelationsRequest request = new FindRelationsRequest();
  request.builder();
  request.setSourceId(sourceId);
  request.setRelationTypeId(relationTypeId)

  PagedResponse<Relation> response = findRelations(request);

  return (response.getTotal() > 0); 

}

def kgiRights_ChangeMembers(String role, List <String> owners, String resource) {

}


def kgiRights_AddMemberStr(String ownerId, String roleId) {
  return kgiRights_AddMember(UUID.fromString(ownerId), UUID.fromString(roleId));
}

def kgiRights_AddMember(UUID ownerId, UUID roleId) {

  AddResponsibilityRequest request = new AddResponsibilityRequest();

  request.setOwnerId(ownerId);
  request.setRoleId(roleId);

  return responsibilityApi.addResponsibility(request);

}

def kgiRights_GetAllMemberUsersByResourceAndRoleStr(String assetId, String roleId) {
  return kgiRights_GetAllMemberUsersByResourceAndRole(UUID.fromString(assetId), UUID.fromString(roleId));
}

def kgiRights_GetAllMemberUsersByResourceAndRole(UUID assetId, UUID roleId) {

  List<User> users = new ArrayList();

  FindResponsibilitiesRequest request = new FindResponsibilitiesRequest();
  request.setResourceIds(Arrays.asList(assetId));
  request.setRoleIds(Arrays.asList(roleId));
  PagedResponse<Responsibility> response = refindResponsibilities(request);

  response.getResults().each { responsibility ->
    ResourceReference reference = responsibility.getOwner();
    User user = apiUser.getUser(reference.getId());
    users.add(user);

  }

  return users;

}

def kgiRights_GetMember(String owner, String resource, String role) {

  Responsibility responsibility = null;
  ResourceReference reference = null;

  FindResponsibilitiesRequest request = new FindResponsibilitiesRequest();
  request.builder();
  request.setOwnerIds(Arrays.asList(owner));
  request.setResourceIds(Arrays.asList(resource));
  request.setRoleIds(Arrays.asList(role));
  PagedResponse<Responsibility> response = apiRole.findResponsibilities(request);

  if (response.getTotal() > 0) {
    responsibility = response.getResults().get(0);
    reference = responsibility.getOwner();
  }

  return reference;

}

def kgiRights_GetMembersByResourceAndRole(String resource, String role) {

  List<User> users = new ArrayList<User>();

  FindResponsibilitiesRequest request = new FindResponsibilitiesRequest();
  request.builder();
  request.setResourceIds(Arrays.asList(resource));
  request.setRoleIds(Arrays.asList(role));
  PagedResponse<Responsibility> response = responsibilityApi.findResponsibilities(request);

  response.getResults().each { responsibility ->
    ResourceReference reference = responsibility.getOwner();
    User user = apiUser.getUser(UUID.fromString(reference.getId()));
    users.add(user);

  }

  return users;

}

def kgiRights_RemoveMember(String user, String role, String resource) {

  Responsibility responsibility = null;
  ResourceReference reference = null;

  FindResponsibilitiesRequest request = new FindResponsibilitiesRequest();
  request.builder();
  request.setOwnerIds(Arrays.asList(owner));
  request.setResourceIds(Arrays.asList(resource));
  request.setRoleIds(Arrays.asList(role));
  PagedResponse<Responsibility> response = responsibilityApi.findResponsibilities(request);

  if (response.getTotal() > 0) {
    responsibility = response.getResults().get(0);
    reference = responsibility.getOwner();
    responsibilityApi.removeResponsibility(reference.getId());
  }


}

def kgiRights_GetRoleByName(String name) {

  String roleId = null;
  FindRolesRequest request = new FindRolesRequest();
  request.builder();
  request.setName(name);
  PagedResponse<Role> response = roleApi.findRoles(request);

  if (response.getTotal() > 0) {
    role = response.getResults().get(0).getId();
  }

  return roleId;

}

def kgiSearch(SearchParameters searchParameters) {
  return searchComponent.search(searchParameters);
}

def kgiSnapshot_Add(String id) {
  
  // The whole snapshot API was removed 

  // https://community.collibra.com/docs/migration/5.1/#MigrationTo51/ref_java-api-changes.htm%3FTocPath%3DAPI%2520changes%2520in%25205.1%7C_____5

  //

}

def kgiSnapshot_GetConfigurations(String id) {

  // The whole snapshot API was removed 

  // https://community.collibra.com/docs/migration/5.1/#MigrationTo51/ref_java-api-changes.htm%3FTocPath%3DAPI%2520changes%2520in%25205.1%7C_____5

  //

}

def kgiUser_GetStr(String userId) {
  return userApi.getUser(UUID.fromString(userId));
}

def kgiUser_Get(UUID userId) {
  return userApi.getUser(userId);
}

def kgiUser_GetByName(String name) {

   User user = null;

   FindUsersRequest request = new FindUsersRequest();
   request.builder();
   request.setName(name);
   PagedResponse<User> users = userApi.findUsers(request);
   if (users.getTotal() > 0) {
     user = users.getResults().get(0);
   }

   return user;

}

def kgiUser_GetCurrent() {

  User user = null;
  Optional<User> optional = userApi.getCurrentUser();
  if (optional.get() != null) {
    user = optional.get();
  }

  return user;

}

def kgiDomain_GetByNameStr(String communityId, String name) {
  return kgiDomain_GetByName(UUID.fromString(communityId), name);
}

def kgiDomain_GetByName(UUID communityId, String name) {

  Domain domain = null;
  FindDomainsRequest request = new FindDomainsRequest();
  request.builder();
  request.setCommunityId(communityId);
  request.setName(name);
  PagedResponse<Domain> domains = domainApi.findDomains(request);
  if (domains > 0) {
    domain = domains.getResults().get(0);
  }

  return domain;

}

def kgiDomain_GetNumberOfTermsStr(String domainId) {
  return kgiDomain_GetNumberOfTerms(UUID.fromString(domainId));
}

def kgiDomain_GetNumberOfTerms(UUID domainId) {

   FindAssetsRequest request = new FindAssetsRequest();
   request.builder();
   request.setDomainId(domainId);

   PagedResponse<Asset> response = assetApi.findAssets(request);
   return response.getTotal();

}

def kgiDomain_GetTermsStr(String domainId, int offset, int limit) {
  return kgiDomain_GetTerms(UUID.fromString(domainId), offset, limit);
}

def kgiDomain_GetTerms(UUID domainId, int offset, int limit) {

   FindAssetsRequest request = new FindAssetsRequest();
   request.builder();
   request.setDomainId(domainId);

   PagedResponse<Asset> response = assetApi.findAssets(request);
   return response.getResults();

}

def kgiDomain_GetTermByNameStr(String domainId, String name) {
  return kgiDomain_GetTermByName(UUID.fromString(domainId), name);
}

def kgiDomain_GetTermByName(UUID domainId, String name) {

   Asset asset = null;
   FindAssetsRequest request = new FindAssetsRequest();
   request.builder();
   request.setDomainId(domainId);
   request.setName(name);

   PagedResponse<Asset> response = assetApi.findAssets(request);

   if (response.getTotal() > 0) {
     asset = response.getResults().get(0);
   }

   return asset;

}

def kgiDomain_Add(UUID communityId, String name, String uri, String description, UUID typeId, String namingRule) {

   AddDomainRequest request = new AddDomainRequest();
   request.builder();
   request.setCommunityId(communityId);
   request.setName(name);
   request.setDescription(description);
   request.setTypeId(typeId);
   // uri and naming rule not used.

   return domainApi.addDomain(request);

}

def kgiDomain_RemoveStr(String domainId) {
  domainApi.removeDomain(UUID.fromString(domainId));
}

def kgiDomain_Remove(UUID domainId) {
  domainApi.removeDomain(domainId);
}

def kgiWorkflow_Cancel(String workflowTaskId, String reason) {
  workflowTaskApi.cancelWorkflowTask(UUID.fromString(workflowTaskId), reason);
}

def kgiWorkflow_GetAllProcessInstances() {
  
  FindWorkflowInstancesRequest request = new FindWorkflowInstancesRequest();
  request.builder();
  PagedResponse<WorkflowInstance> response = workflowInstanceApi.findWorkflowInstances(request);
  return response.getResults();

}

def kgiWorkflow_GetAllTasks(String id) {

  FindWorkflowTasksRequest request = new FindWorkflowTasksRequest();
  request.builder();
  PagedResponse<WorkflowTask> response = workflowTaskApi.findWorkflowTasks(request);
  return response.getResults();

}

def kgiWorkflow_GetDefinitionByProcessId(String processId) {

  WorkflowInstance foundInstance = null;
  FindWorkflowInstancesRequest request = new FindWorkflowInstancesRequest();
  request.builder();
  PagedResponse<WorkflowInstance> response = workflowInstanceApi.findWorkflowInstances(request);
  List<WorkflowInstance> results = response.getResults();
  results.each { instance ->
    if (instance.getId().equals(processId)) {
      foundInstance = instance;
    }

  }

  return foundInstance;

}

def kgiWorkflow_GetDefinitions() {

  FindWorkflowDefinitionsRequest request = new FindWorkflowDefinitionsRequest();
  request.builder();
  PagedResponse<WorkflowDefinition> response = workflowDefinitionApi.findWorkflowDefinitions(request);
  return response.getResults();

}

def kgiWorkflow_GetXML(String workflowDefinitionId) {
  return getWorkflowDefinitionXML(UUID.fromString(workflowDefinitionId));
}

def kgiWorkflow_GetProcessInstances(String workflowDefinitionId) {

  FindWorkflowInstancesRequest request = new FindWorkflowInstancesRequest();
  request.builder();
  request.setWorkflowDefinitionId(UUID.fromString(workflowDefinitionId));
  PagedResponse<WorkflowInstance> response = workflowInstanceApi.findWorkflowInstances(request);
  return response.getResults();

}

def kgiWorkflow_GetProcessInstancesByItemStr(String assetId, String workflowDefinitionId) {
  return kgiWorkflow_GetProcessInstancesByItem(UUID.fromString(assetId), workflowDefinitionId);
}

def kgiWorkflow_GetProcessInstancesByItem(UUID assetId, String workflowDefinitionId) {

  FindWorkflowInstancesRequest request = new FindWorkflowInstancesRequest();
  request.builder();
  request.setWorkflowDefinitionId(workflowDefinitionId);
  PagedResponse<WorkflowInstance> response = workflowInstanceApi.findWorkflowInstances(request);
  List<WorkflowInstance> instances = new ArrayList<WorkflowInstance>();

  response.getResults().each { instance ->

    if (instance.getBusinessItem().getId().equals(assetId)) {
      instances.add(instance);
    }

  }

  return instances;

}

def kgiWorkflow_ReassignTask(String task, List <String> users, List <String> groups, List <String> roles, List <String> roleCommunities) {
  // I don't think it's possible to implement this workflow 
}

def kgiWorkflow_SetConfigurationVariables(String workflow, java.util.Map<String,String> configurationVariables) {
  // I don't think it's possible to implement this workflow 
}

def kgiWorkflow_Start(String workflowDefinitionId, UUID businessItemId, ResourceType resourceType, Map<String,String> formProperties) {

  WorkflowBusinessItemType businessItemType = WorkflowBusinessItemType.ASSET;
  if ("Term".equals(resourceType.toString())) {
    businessItemType = WorkflowBusinessItemType.ASSET;
  } else if ("Community".equals(resourceType.toString())) {
    businessItemType = WorkflowBusinessItemType.COMMUNITY;
  } else if ("Domain".equals(resourceType.toString())) {
    businessItemType = WorkflowBusinessItemType.DOMAIN;
  // } else if ("GL".equals(resourceType)) {
  //   businessItemType = WorkflowBusinessItemType.GLOBAL;
  }

  StartWorkflowInstancesRequest request = new StartWorkflowInstancesRequest();

  request.builder();
  request.setWorkflowDefinitionId(UUID.fromString(workflowDefinitionId));
  request.setBusinessItemType(businessItemType);
  request.setBusinessItemIds(Arrays.asList(businessItemId));
  request.setFormProperties(formProperties)
  workflowInstanceApi.startWorkflowInstances(request);

}

