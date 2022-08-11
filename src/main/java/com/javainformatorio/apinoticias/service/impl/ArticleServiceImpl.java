package com.javainformatorio.apinoticias.service.impl;

import com.javainformatorio.apinoticias.dto.ArticleDTO;
import com.javainformatorio.apinoticias.entities.ArticleEntity;
import com.javainformatorio.apinoticias.mapper.ArticleMapper;
import com.javainformatorio.apinoticias.repository.ArticleRepository;
import com.javainformatorio.apinoticias.repository.AuthorRepository;
import com.javainformatorio.apinoticias.repository.SourceRepository;
import com.javainformatorio.apinoticias.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;
    private final SourceRepository sourceRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper,
                              ArticleRepository articleRepository,
                              SourceRepository sourceRepository,
                              AuthorRepository authorRepository){
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
        this.sourceRepository = sourceRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        sourceRepository.findById(articleDTO.getSource().getId()).orElseThrow(
                () -> new RuntimeException("Not found source id : " + articleDTO.getSource().getId())
        );

        ArticleEntity articleEntity = articleMapper.toEntity(articleDTO);
        ArticleEntity articleSave = articleRepository.save(articleEntity);

        return articleMapper.toDTO(articleSave);
    }
}