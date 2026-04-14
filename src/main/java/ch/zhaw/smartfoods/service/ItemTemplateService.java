package ch.zhaw.smartfoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplate;
import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplateCreateDTO;
import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplateUpdateDTO;
import ch.zhaw.smartfoods.repository.ItemTemplateRepository;

@Service
public class ItemTemplateService {
    @Autowired
    ItemTemplateRepository itemTemplateRepository;

    public ItemTemplate createTemplate(ItemTemplateCreateDTO dto) {
        if (itemTemplateRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException(
                    "Es ist bereits ein Template mit diesem Namen hinterlegt");
        }
        ItemTemplate template = new ItemTemplate(
                dto.getName(),
                dto.getDefaultUnit(),
                dto.getDefaultPrice(),
                dto.getDefaultAmount());
        return itemTemplateRepository.save(template);
    }

    public List<ItemTemplate> getAllTemplates(){
            return itemTemplateRepository.findAll();

    }

    public ItemTemplate getTemplateById(String id) {
    return itemTemplateRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Template nicht gefunden"));
}

    public void deleteTemplate(String id) {
        if (!itemTemplateRepository.existsById(id)) {
            throw new RuntimeException("Template nicht gefunden");
        }
        itemTemplateRepository.deleteById(id);
    }

    public ItemTemplate updateTemplate(String id, ItemTemplateUpdateDTO dto) {
        ItemTemplate template = itemTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template nicht gefunden"));

        if (dto.getName() != null)
            template.setName(dto.getName());
        if (dto.getDefaultUnit() != null)
            template.setDefaultUnit(dto.getDefaultUnit());
        if (dto.getDefaultPrice() != null)
            template.setDefaultPrice(dto.getDefaultPrice());
        if (dto.getDefaultAmount() != null)
            template.setDefaultAmount(dto.getDefaultAmount());

        return itemTemplateRepository.save(template);
    }
}
