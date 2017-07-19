(function(){
var sspInterviewApp = angular.module("sspInterviewApp", []);
sspInterviewApp.controller("interviewCtrl", function ($scope) {
    $scope.priorities = [
        {value: 0, label: "Содержание работы (интересные, значимые для Вас задачи,  проекты и др.)", show: -1},
        {value: 1, label: "Уровень оплаты труда (зарплата, премии)", show: -1},
        {value: 2, label: "Возможность больше зарабатывать от приложенных усилий", show: -1},
        {
            value: 3,
            label: "Признание руководством и коллегами Ваших профессиональных и трудовых заслуг (Доска почета, грамота и т.п.)",
            show: -1
        },
        {value: 4, label: "Условия работы", show: -1},
        {value: 5, label: "Перспектива построения карьеры", show: -1},
        {value: 6, label: "Повышение профессионализма", show: -1},
        {value: 7, label: "Доброжелательное отношение руководителей к подчиненным", show: -1},
        {value: 8, label: "Атмосфера в команде, хороший, дружный коллектив", show: -1},
        {value: 9, label: "Иной", show: -1}
    ];
    $scope.isHidden = function (index, item) {
        var i = $scope.priorities.indexOf(item);
        if ($scope.priorities[i].show == index || $scope.priorities[i].show == -1) { //alert($scope.priorities[i].show);
            return false;
        }
        return true;
    };
    $scope.changeOptions = function (selected, index) {
        $scope.priorities[selected-1].show = index;
        $scope.priorities.forEach(function (item, i, arr) {
            if (item.show == index && i != selected-1) {
                item.show = -1;
            }
        })
    };
    $scope.isOthers = function () {
        if ($scope.priorities[9].show == -1) {
            return true;
        }
        return false;
    };

    $scope.fivepoint = [
        {id: 0, value: "Четко ли распределены функции и задачи в подразделении?"},
        {
            id: 1,
            value: "Понимаете ли Вы что должны делать на своей работе, чтобы способствовать достижению целей компании?"
        },
        {id: 2, value: "Устраивает ли Вас обеспечение рабочего места всем необходимым?"},
        {id: 3, value: "Устраивает ли Вас содержание самой работы?"},
        {id: 4, value: "Получаете ли Вы достаточно информации для решения поставленных задач?"},
        {
            id: 5,
            value: "Знаете ли Вы к кому можно обратиться для решения ваших проблем, кроме непосредственного руководителя?"
        },
        {id: 6, value: "Ощущаете ли Вы помощь и поддержку в работе со стороны руководителя подразделения?"},
        {
            id: 7,
            value: "Рассматриваются ли руководителем подразделения Ваши предложения по усовершенствованию работы?"
        },
        {
            id: 8,
            value: "Если вы имеете дополнительный источник дохода в виде подработки, оцените насколько он значим для Вас?"
        },
        {
            id: 9,
            value: "Понимаете ли Вы принцип начисления дополнительных бонусов к заработной плате и что нужно делать для их получения?"
        },
        {
            id: 10,
            value: "Прикладываете ли Вы усилия для достижения больших результатов и получения дополнительных бонусов?"
        },
        {
            id: 11,
            value: "Учитываются ли Ваши усилия и достижения при начислении бонусов руководителем направления?"
        },
        {id: 12, value: "Имеете ли Вы возможность повышения своей квалификации?"},
        {id: 13, value: "Устраивают ли Вас взаимоотношения с коллегами по работе?"},
        {id: 14, value: "Устраивает ли вас состояние вспомогательных помещений (кухня, душ…)?"}
    ];

    $scope.yesno = [
        {id: 0, value: "Дает ли уверенность в завтрашнем дне работа в компании?"},
        {
            id: 1,
            value: "Есть ли у Вас «командный дух»? Готовы ли Вы без особых просьб помочь коллеге при решении задачи?"
        },
        {id: 2, value: "Останетесь ли вы после работы помочь коллеге, который не успевает завершить задачу?"},
        {id: 3, value: "Готовы ли вы участвовать в спортивных соревнованиях, проводимых компанией?"},
        {id: 4, value: "Готовы ли вы принимать участие в соревнованиях между командами?"}
    ];

    $scope.question = [
        "Я удовлетворен(а) работой в компании и не хотел(а) бы менять место работы",
        "Я удовлетворен(а) работой в компании, но хотел(а) бы сменить место работы по объективным обстоятельствам",
        "Я не удовлетворен(а) работой в компании, но не хотел(а) бы менять место работы",
        "Я не удовлетворен(а) работой в компании и хотел (а) бы сменить место работы",
        "Я не задумывался (не задумывалась) над этим вопросом"
    ];
    $scope.loyality = [
        {
            id: 0,
            value: "Я готов приложить усилия, даже превышающие общепринятые ожидания, чтобы моя компания преуспевала"
        },
        {id: 1, value: "Я всегда говорю своим друзьям, что работаю в великолепной компании"},
        {id: 2, value: "Я не испытываю никакой привязанности по отношению к этой компании"},
        {id: 3, value: "Я соглашусь практически с любым назначением, лишь бы остаться работать в этой компании"},
        {id: 4, value: "Я считаю, что мои личные ценности и ценности, принятые в моей компании, очень близки"},
        {id: 5, value: "Я с гордостью заявляю другим, что являюсь частью этой компании"},
        {
            id: 6,
            value: "С таким же успехом я работал бы в любой другой компании, если бы можно было выполнять аналогичную работу"
        },
        {
            id: 7,
            value: "С таким же успехом я работал бы в любой другой компании, если бы можно было выполнять аналогичную работу"
        },
        {id: 8, value: "Моя компания действительно вдохновляет меня работать как можно лучше"},
        {id: 9, value: "Моя компания действительно вдохновляет меня работать как можно лучше"},
        {
            id: 10,
            value: "Требуются очень незначительные изменения в моих личных обстоятельствах, чтобы я оставил работу в этой компании"
        },
        {
            id: 11,
            value: "Я очень рад, что выбрал именно эту компанию, когда искал работу и рассматривал другие предложения"
        },
        {id: 12, value: "Не имеет смысла надолго задерживаться в этой компании "},
        {
            id: 13,
            value: "Во многих случаях я не согласен с основными направлениями политики компании по отношению к своим сотрудникам"
        },
        {id: 14, value: "Мне действительно небезразлична судьба компании"},
        {id: 15, value: "Для меня это самая лучшая из компаний, где я мог бы работать"},
        {id: 16, value: "Решение начать работать в этой компании было, безусловно, ошибкой с моей стороны"}
    ];
    $scope.nextButton = function () {
        $('#firstPart select, #firstPart input').filter('[required]:visible').each(function () {
            if ($(this).val() == "" || $(this).val() == null || $(this).val() == undefined)
                $(this).parents('.form-group').addClass('has-error');
            else
                $(this).parents('.form-group').removeClass('has-error');
        });

        $('#firstPart .btn-group-vertical, #firstPart .btn-group').has("input[required]").each(function () {
            $(this).removeClass('has-error');
            if ($(this).find("input").length > 0 && $(this).find("input:checked").length <= 0)
                $(this).addClass('has-error');

        });
        $('#firstPart .has-error select, #firstPart .has-error input').first().focus();
        if ($('#firstPart .has-error').length <= 0) {
            $('#firstPart').css('display', 'none');
            $('#secondPart').css('display', 'block');
        }
    };


    $scope.finishButton = function () {
        $('#secondPart .btn-group-vertical, #secondPart .btn-group').has("input[required]").each(function () {
            $(this).removeClass('has-error');
            if ($(this).find("input").length > 0 && $(this).find("input:checked").length <= 0)
                $(this).addClass('has-error');

        });

        $('#secondPart .has-error select, #secondPart .has-error input').first().focus();
        if ($('#secondPart .has-error').length <= 0) {
            var items = [];
            $('#priorities select').not('#last').each(function (index) {
                items.push({name :index+1, value: $(this).val()});
            });
            items.push({name : 10, value : $('#last option').not('.ng-hide').text()});
            items.sort(function (a,b) {
                if(parseInt(a.value) > parseInt(b.value)) {
                    return 1;
                }
                if(a.value < b.value) {
                    return -1;
                }
                return 0;

            });

            var priorities = [];
            items.forEach(function (element, index, array) {
                priorities.push(element.name);
            });

            var other_priority = $('#firstPart input:text').val();

            var fivepoint = [];
            $('#fivepoint .btn-group').each(function () {
               fivepoint.push(parseInt($(this).find('input:checked').val()));
            });

            var yesno = [];
            $('#yesno .btn-group').each(function () {
                yesno.push(parseInt($(this).find('input:checked').val()));
            });

            var question = parseInt($('#question .btn-group-vertical').find('input:checked').val());
            var additional = $('#additional textarea').val();

            var loyality = [];
            $('#loyality .btn-group').each(function () {
                loyality.push(parseInt($(this).find('input:checked').val()));
            });

            var req = {
                priorities : priorities,
                other_priority: other_priority,
                fivepoint : fivepoint,
                yesno : yesno,
                question : question,
                additional : additional,
            };

            $.ajax({
                type: 'POST',
                url: 'https://ssp-interview.appspot.com//tosheet',
                data: "request="+JSON.stringify(req)
            });
            $('#secondPart').css('display', 'none');
            $('#thirdPart').css('display', 'block');
        }
    };
    $scope.previousButton = function () {
        $('#secondPart').css('display', 'none');
        $('#firstPart').css('display', 'block');
    }
});
})();