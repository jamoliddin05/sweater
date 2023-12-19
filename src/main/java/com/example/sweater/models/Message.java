package com.example.sweater.models;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@ToString(of = {"id", "text", "tag"})
@NoArgsConstructor
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Пожалуйста заполните поле")
    @Length(max = 2048, message = "Сообщение не должно превышать 2048 символов")
    private String text;

    @Length(max = 255, message = "Не превышайте 255 символов")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public Long getId() {
        return this.id;
    }

    public @NotBlank(message = "Пожалуйста заполните поле") @Length(max = 2048, message = "Сообщение не должно превышать 2048 символов") String getText() {
        return this.text;
    }

    public @Length(max = 255, message = "Не превышайте 255 символов") String getTag() {
        return this.tag;
    }

    public User getAuthor() {
        return this.author;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(@NotBlank(message = "Пожалуйста заполните поле") @Length(max = 2048, message = "Сообщение не должно превышать 2048 символов") String text) {
        this.text = text;
    }

    public void setTag(@Length(max = 255, message = "Не превышайте 255 символов") String tag) {
        this.tag = tag;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Message)) return false;
        final Message other = (Message) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$text = this.getText();
        final Object other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) return false;
        final Object this$tag = this.getTag();
        final Object other$tag = other.getTag();
        if (this$tag == null ? other$tag != null : !this$tag.equals(other$tag)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        final Object this$filename = this.getFilename();
        final Object other$filename = other.getFilename();
        if (this$filename == null ? other$filename != null : !this$filename.equals(other$filename)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Message;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $text = this.getText();
        result = result * PRIME + ($text == null ? 43 : $text.hashCode());
        final Object $tag = this.getTag();
        result = result * PRIME + ($tag == null ? 43 : $tag.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        final Object $filename = this.getFilename();
        result = result * PRIME + ($filename == null ? 43 : $filename.hashCode());
        return result;
    }
}
