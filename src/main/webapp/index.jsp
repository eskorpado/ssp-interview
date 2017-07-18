<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" ng-app="sspInterviewApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Оценки удовлетворенности персонала ${name}</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Angular -->
    <script src="js/angular.min.js"></script>
    <!-- Common -->
    <script src="js/common.js"></script>
</head>
<body ng-controller="interviewCtrl">
<div class="customHeader"></div>
<div class="container" ng-cloak>
    <form class="form-horizontal">
        <div class="col-xs-10 col-xs-offset-1 section" id="firstPart">
            <div class="row">
                <div class="col-xs-12">
                    <h1>Оценки удовлетворенности персонала</h1>
                    <b>
                        <p>Руководство компании и служба персонала проводят анкетирование сотрудников с целью
                            оптимизации
                            кадровой политики компании. Ваше мнение будет обязательно учтено при разработке мер
                            повышения
                            эффективности системы управления персоналом. Анкета является анонимной. При ответах на
                            вопросы
                            Вам необходимо отметить ответ, соответствующий Вашему мнению.</p>
                    </b>
                </div>
            </div>
            <!-- FIRST SECTION -->
            <div class="row top50" id="priorities">
                <div class="col-xs-12 alert alert-info" style="margin-left: 0">
                    <h4>1. Выстройте по степени значимости для Вас факторы, которые представлены ниже, первый - самый
                        значимый для Вас, далее по степени убывания, последний – самый незначительный:</h4>
                </div>
                <div class="form-group " ng-repeat="i in [0,1,2,3,4,5,6,7,8]">
                    <select class="col-xs-12 form-control" name="priority{{i}}" id="priority{{i}}" ng-model="selected"
                            ng-change="changeOptions(selected,{{i}})" required>
                        <option value="" disabled selected>Фактор с приоритетом {{i+1}}</option>
                        <option ng-repeat="prio in priorities" value="{{$index+1}}" ng-hide="isHidden(i,prio)">
                            {{prio.label}}
                        </option>
                    </select>
                </div>
                <div class="form-group" ng-hide="isOthers()">
                    <input type="text" class="form-control col-xs-12" name="priority_other" id="otherPriorities"
                           placeholder="Инные (укажите):" required>
                </div>
            </div>
            <div class="row top50" id="fivepoint">
                <div class="col-xs-12 alert alert-info">
                    <h4>2. Оцените по пятибальной шкале следующие вопросы:</h4>
                </div>
                <div class="col-xs-12">
                    <p>0 – если данные отсутствуют</p>
                    <p>1 – очень плохо/полностью не соответствует Вашим представлениям</p>
                    <p>2 – плохо/ не соответствует Вашим представлениям</p>
                    <p>3 – удовлетворительно/ частично не соответствует Вашим представлениям</p>
                    <p>4 – хорошо/ практически полностью соответствует Вашим представлениям</p>
                    <p>5 – отлично/ полностью соответствует Вашим представлениям</p>
                </div>
                <div class="col-xs-12 top20" ng-repeat="fp in fivepoint">
                    <h4>{{fp.value}}</h4>
                    <div class="btn-group" data-toggle="buttons">
                        <label class="btn btn-default" ng-repeat="i in [0,1,2,3,4,5]">
                            <input type="radio" name="fivepoint{{fp.id}}" value="{{i}}" required> {{i}}
                        </label>
                    </div>
                </div>
            </div>
            <div class="row top50" id="yesno">
                <div class="col-xs-12 alert alert-info">
                    <h4>3. Ответьте да/нет на следующие вопросы:</h4>
                </div>
                <div class="col-xs-12 top20" ng-repeat="yn in yesno">
                    <h4>{{yn.value}}</h4>
                    <div class="btn-group" data-toggle="buttons">
                        <label class="btn btn-default">
                            <input type="radio" name="yesno{{yn.id}}" value="1" required> Да
                        </label>
                        <label class="btn btn-default">
                            <input type="radio" name="yesno{{yn.id}}" value="0" required> Нет
                        </label>
                    </div>
                </div>
            </div>
            <div class="row top50" id="question">
                <div class="col-xs-12 alert alert-info">
                    <h4>4. Отметьте, пожалуйста, то утверждение, которое соответствует Вашей позиции:</h4>
                </div>
                <div class="col-xs-12 top20" style="padding-left: 0; padding-right: 0">
                    <div class="btn-group-vertical" style="width: 100%;" data-toggle="buttons">
                        <label class="btn btn-default" ng-repeat="qu in question" class="top10">
                            <input type="radio" name="question" value="{{$index}}" required>{{qu}}
                        </label>
                    </div>
                </div>
            </div>
            <div class="row top50" id="additional">
                <div class="col-xs-12 alert alert-info">
                    <h4>5. Если у Вас есть желание высказаться, напишите, пожалуйста, Ваши пожелания по улучшению
                        бизнеса нашей компании (Вы можете пропустить ответ на этот вопрос)</h4>
                </div>
                <div class="form-group">
                    <textarea name="additional" rows="5" class="col-xs-12 form-control top10"></textarea>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-default btn-lg col-xs-4 top10" ng-click="nextButton()">Далее
                    </button>
                </div>
            </div>
        </div>

        <!-- SECOND SECTION -->
        <div class="col-xs-10 col-xs-offset-1 section" id="secondPart">
            <div class="row">
                <div class="col-xs-12">
                    <h1>Оценка уровня лояльности сотрудников</h1>
                </div>
            </div>
            <div class="row top20">
                <div class="col-xs-12 alert alert-info" style="margin-left: 0">
                    <h4>Вам предложено несколько вопросов, выражающих разнообразные чувства, которые Вы может испытывать
                        к
                        нашей компании. Определите свои собственные чувства. Для этого выберите приведенный
                        вариант:</h4>
                </div>
                <div class="col-xs-12">
                    <p>1 – абсолютно не согласен;</p>
                    <p>2 – в чем-то не согласен;</p>
                    <p>3 – затрудняюсь ответить;</p>
                    <p>4 – согласен до некоторой степени;</p>
                    <p>5 – полностью согласен.</p>
                </div>
                <div class="col-xs-12 top20" ng-repeat="loy in loyality">
                    <h4>{{loy.value}}</h4>
                    <div class="btn-group" data-toggle="buttons">
                        <label class="btn btn-default" ng-repeat="i in [1,2,3,4,5]">
                            <input type="radio" name="loyality{{loy.id}}" value="{{i}}" required> {{i}}
                        </label>
                    </div>
                </div>
            </div>
            <div class="row top20">
                <div class="col-xs-12 alert alert-info" style="margin-left: 0">
                    <h4>Спасибо за Ваши ответы!</h4>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-default btn-lg col-xs-4 top10" ng-click="previousButton()"
                            style="margin-right: 10px">Назад
                    </button>
                    <button type="button" class="btn btn-primary btn-lg col-xs-4 top10" ng-click="finishButton()">
                        Отправить
                    </button>
                </div>
            </div>
        </div>
    </form>

    <!-- THIRD SELECTION -->
    <div class="col-xs-10 col-xs-offset-1 section" id="thirdPart">
        <div class="row">
            <div class="col-xs-12">
                <h1>Оценка уровня лояльности сотрудников</h1>
            </div>
        </div>
        <div class="row top20">
            <div class="col-xs-12 alert alert-success" style="margin-left: 0">
                <h4>Спасибо, ответ записан.</h4>
            </div>
        </div>
    </div>
</div>




</body>
</html>