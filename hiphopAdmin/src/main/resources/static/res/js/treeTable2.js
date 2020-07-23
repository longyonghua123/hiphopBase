var options; (function(d) {
    var b = false;
    var g = false;
    var c = [];
    d.fn.acts_as_tree_table = function(j) {
        options = d.extend({},
        d.fn.acts_as_tree_table.defaults, j);
        return this.each(function() {
            var k = d(this);
            if (k.attr("mode") == "ajax" || k.attr("ajaxMode") == "true" || k.attr("ajaxMode") == true) {
                b = true
            } else {
                b = false
            }
            if (k.attr("checkMode") == "true" || k.attr("checkMode") == true) {
                g = true
            } else {
                g = false
            }
            k.addClass("acts_as_tree_table");
            if (b) {
                k.children("tbody").children("tbody tr").each(function() {
                    var l = d(this);
                    if (l.not(".parent") && l.attr("url") != null) {
                        l.addClass("parent")
                    }
                    if (l.is(".parent")) {
                        i(l)
                    }
                })
            } else {
                k.children("tbody").children("tbody tr").each(function(l) {
                    var m = d(this);
                    if (m.not(".parent") && children_of(m).size() > 0) {
                        m.addClass("parent")
                    }
                    if (m.is(".parent")) {
                        f(m)
                    } else {
                        if (l != 0) {
                            a(m)
                        }
                    }
                })
            }
        })
    };
    d.fn.acts_as_tree_table.defaults = {
        expandable: true,
        default_state: "expanded",
        indent: 19,
        tree_column: 0
    };
    d.fn.collapse = function() {
        collapse(this)
    };
    d.fn.expand = function() {
        expand(this)
    };
    d.fn.toggleBranch = function() {
        toggle(this)
    };
    function a(m) {
        if (g) {
            var j = d(m.children("td")[options.tree_column]);
            var l = m.attr("id");
            var k = m.attr("class");
            j.prepend('<input type="checkbox" id="check-' + l + '" class="check-' + k + '" style="margin-top:6px" onclick=selectCheckbox("' + l + '")>')
        }
    }
    function f(m) {
        var j = d(m.children("td")[options.tree_column]);
        var n;
        if (g) {
            n = parseInt(j.css("padding-left")) + options.indent * 2
        } else {
            n = parseInt(j.css("padding-left")) + options.indent
        }
        children_of(m).each(function() {
            d(d(this).children("td")[options.tree_column]).css("padding-left", n + "px")
        });
        if (g) {
            var l = m.attr("id");
            var k = m.attr("class");
            j.prepend('<input type="checkbox" id="check-' + l + '" class="check-' + k + '" style="margin-top:6px" onclick=javascript:selectCheckbox("' + l + '")>')
        }
        if (options.expandable) {
            j.prepend('<span style="margin-left:0px; padding-left: ' + options.indent + 'px" class="expander"></span>');
            var o = d(j[0].firstChild);
            o.click(function() {
                toggle(m)
            });
            if (! (m.is(".expanded") || m.is(".collapsed"))) {
                m.addClass(options.default_state)
            }
            if (m.is(".collapsed")) {
                collapse(m)
            } else {
                if (m.is(".expanded")) {
                    expand(m)
                }
            }
        }
    }
    function i(m) {
        var j = d(m.children("td")[0]);
        if (g) {
            var l = m.attr("id");
            var k = m.attr("class");
            j.prepend('<input type="checkbox" id="check-' + l + '" class="check-' + k + '" style="margin-top:6px" onclick=javascript:selectCheckbox("' + l + '")>')
        }
        j.prepend('<span style="margin-left:0px; padding-left: ' + options.indent + 'px" class="expander"></span>');
        var n = d(j[0].firstChild);
        m.addClass("collapsed");
        n.click(function() {
            var p = d(this);
            var o = m.attr("url");
            if (o != "") {
                m.removeClass("collapsed");
                m.addClass("loading");
                window.setTimeout(function() {
                    e(p, o, m)
                },
                200)
            } else {
                toggle(m)
            }
        })
    }
    function h(m) {
        var j = d(m.children("td")[0]);
        if (g) {
            var l = m.attr("id");
            var k = m.attr("class")
        }
        j.prepend('<span style="margin-left:0px; padding-left: ' + options.indent + 'px" class="expander"></span>');
        var n = d(j[0].firstChild);
        m.addClass("collapsed");
        n.click(function() {
            var p = d(this);
            var o = m.attr("url");
            if (o != "") {
                m.removeClass("collapsed");
                m.addClass("loading");
                window.setTimeout(function() {
                    e(p, o, m)
                },
                200)
            } else {
                toggle(m)
            }
        })
    }
    function e(l, k, j) {
        d.ajax({
            url: k,
            error: function() {
                try {
                    top.Dialog.alert("数据加载失败，请检查dataPath是否正确")
                } catch(m) {
                    alert("数据加载失败，请检查dataPath是否正确")
                }
            },
            success: function(o) {
                var n = l.parents("tr");
                var p = d(o);
                n.after(p);
                n.attr("url", "");
                n.removeClass("loading");
                n.addClass("expanded");
                var m = n.find("td").eq(0);
                var q;
                if (g) {
                    q = parseInt(m.css("padding-left")) + options.indent * 2
                } else {
                    q = parseInt(m.css("padding-left")) + options.indent
                }
                children_of(n).each(function() {
                    d(d(this).children("td")[0]).css("padding-left", q + "px");
                    var r = d(this);
                    if (g) {
                        var u = r.attr("id");
                        var t = r.attr("class");
                        var s = check_parent_of(t);
                        if (s.attr("checked") == true || s.attr("checked") == "true") {
                            d(d(this).children("td")[0]).prepend('<input checked="true" type="checkbox" id="check-' + u + '" class="check-' + t + '" style="margin-top:6px" onclick=javascript:selectCheckbox("' + u + '")>')
                        } else {
                            d(d(this).children("td")[0]).prepend('<input type="checkbox" id="check-' + u + '" class="check-' + t + '" style="margin-top:6px" onclick=javascript:selectCheckbox("' + u + '")>')
                        }
                    }
                    if (r.not(".parent") && r.attr("url") != null) {
                        r.addClass("parent")
                    }
                    if (r.is(".parent")) {
                        h(r)
                    }
                })
            }
        })
    }
})(jQuery);
function selectCheckbox(b) {
    var a = document.getElementById("check-" + b).checked;
    check_children_of(b).each(function() {
        var c = $(this);
        c[0].checked = a;
        selectCheckbox($(this).parents("tr").attr("id"))
    })
}
function children_of(a) {
    return $("tr.child-of-" + a[0].id)
}
function check_children_of(a) {
    return $(".check-child-of-" + a)
}
function check_parent_of(b) {
    var a = b.substring(9, b.length);
    return $("#check-" + a)
}
function toggle(a) {
    if (a.is(".collapsed")) {
        a.removeClass("collapsed");
        a.addClass("expanded");
        expand(a)
    } else {
        a.removeClass("expanded");
        a.addClass("collapsed");
        collapse(a)
    }
    if ($(".cusTreeTable").length > 0) {
        $(".cusTreeTable").each(function() {
        	$(this).find('[class^=child-of-node-]').find('span').filter('.img_remove2').removeClass().addClass('img_add2 hand');
            $(this).find("tr").filter(":has(table)").hide();
        })
    }
}
function collapse(a) {
    children_of(a).each(function() {
        var b = $(this);
        collapse(b);
        b.hide()
    })
}
function expand(a) {
    children_of(a).each(function() {
        var b = $(this);
        if (b.is(".expanded.parent")) {
            expand(b)
        }
        b.show()
    })
}
$(function() {
    $(".treeTable").each(function() {
        var b = true;
        var a = "expanded";
        if ($(this).attr("expandable") == "false") {
            b = false
        }
        if ($(this).attr("initState") == "collapsed") {
            a = "collapsed"
        }
        $(this).acts_as_tree_table({
            expandable: b,
            default_state: a
        })
    })
});