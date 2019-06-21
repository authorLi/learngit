package com.mycclee.blog.service.impl;

import com.mycclee.blog.ESRepository.EsBlogRepository;
import com.mycclee.blog.beans.EsBlog;
import com.mycclee.blog.service.EsBlogService;
import org.elasticsearch.search.SearchParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

@Service
public class EsBlogServiceImpl implements EsBlogService {

    @Autowired
    private EsBlogRepository esBlogRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void removeEsBlog(EsBlog esBlog) {
        esBlogRepository.delete(esBlog);
    }

    @Override
    public EsBlog updateEsBlog(EsBlog esBlog) {
        return esBlogRepository.save(esBlog);
    }

    @Override
    public EsBlog getEsBlogByBlogId(Long blogId) {
        return esBlogRepository.findByBlogId(blogId);
    }

    @Override
    public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) throws SearchParseException {
        Page<EsBlog> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        if (pageable.getSort() == null){
            pageable = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        pages = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword,pageable);
        return pages;
    }

    @Override
    public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) throws SearchParseException{
        Page<EsBlog> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"(comments + likes)");
        if (pageable.getSort() == null){
            pageable = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        }
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword,pageable);
    }

    @Override
    public Page<EsBlog> listEsBlogs(Pageable pageable) {
        return esBlogRepository.findAll(pageable);
    }
}
