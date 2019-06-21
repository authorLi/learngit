package com.mycclee.blog.ESRepository;

import com.mycclee.blog.beans.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Long> {

    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,String summary,String content,String tags,Pageable pageable);

    EsBlog findByBlogId(Long blogId);
}
