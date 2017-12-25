if (typeof tinyMCE !==  'undefined' && typeof tinyMCE !=  'function') {
   tinymce.init({
    mode: "specific_textareas",
    editor_selector: "mceEditor",
    theme: "modern",
    skin:"lightgray-no-fonts",
    width: "95%",
    height: "250",
    browser_spellcheck: true,
    remove_linebreaks : false,

    forced_root_block_attrs:{"style": "padding:0;margin:2px 0;"},

    plugins: [
        "advlist autolink link image lists charmap print preview hr anchor pagebreak searchreplace",
        "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
        "table directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern save"
    ],

    toolbar1: "save,newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect  fontselect fontsizeselect editselect ",
    toolbar2: "cut copy paste | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image code searchreplace | insertdatetime preview | forecolor backcolor ",
    toolbar3: "table| hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | visualchars visualblocks nonbreaking template pagebreak restoredraft",

    theme_modern_resizing: true,
    theme_modern_toolbar_location: "top",
    theme_modern_toolbar_align: "left",
    theme_modern_statusbar_location: "bottom",

    save_enablewhendirty : true,

    menubar: false,
    toolbar_items_size: 'small',

    file_picker_callback: function(callback, value, meta) {
        if (meta.filetype == 'image') {
            $('#upload').trigger('click');
            $('#upload').on('change', function() {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function(e) {
                    callback(e.target.result, {
                        alt: ''
                    });
                };
                reader.readAsDataURL(file);
            });
        }
    },

    save_onsavecallback: function() {
        $(".save").click()
    },

    style_formats: [{
        title: 'Bold text',
        inline: 'b'
    }, {
        title: 'Red text',
        inline: 'span',
        styles: {
            color: '#ff0000'
        }
    }, {
        title: 'Red header',
        block: 'h1',
        styles: {
            color: '#ff0000'
        }
    }, {
        title: 'Example 1',
        inline: 'span',
        classes: 'example1'
    }, {
        title: 'Example 2',
        inline: 'span',
        classes: 'example2'
    }, {
        title: 'Table styles'
    }, {
        title: 'Table row 1',
        selector: 'tr',
        classes: 'tablerow1'
    }],

    templates: [{
        title: 'Test template 1',
        content: 'Test 1'
    }, {
        title: 'Test template 2',
        content: 'Test 2'
    }],

    class_filter : function(cls, rule) {
        return cls == 'scayt-ignore' ? false : cls;
        },


      pagebreak_separator: '<div style="page-break-before: always;"></div>',

    font_formats: "Gotham=gotham,helvetica neue,helvetica,arial,sans-serif;" +
                "Times New Roman=times new roman,times;" +
                "Arial=arial,helvetica,sans-serif;",

    block_formats: 'Paragraph =p; address =italic;Header 1=h1;Header 2=h2;Header 3=h3;Header 4=h4;Header 5=h5;Header 6=h6'

});

}