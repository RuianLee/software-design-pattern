
public class Monster extends Role{
    public Monster(Game game) {
        super(game, 'M', 1);
    }

    public void command() {
        executeStateEffect();
        autoAction();
    }

    /**
     * 怪物死亡，從地圖和遊戲中移除
     */
    @Override
    protected void die() {
        System.out.println("Monster at (" + getCrood().getX() + ", " + getCrood().getY() + ") has been defeated!");
        getGame().removeMonster(this);       // 從 Game 的列表移除
        getGameMap().removeFromMap(this);    // 從地圖移除
    }

    public void autoAction() {
        String heroDirection = getGameMap().checkHeroAround(getCrood());

        if (heroDirection != null) {
            System.out.println("Monster detected Hero at " + heroDirection + " and attacks!");
            attackHero(heroDirection);
        } else {
            System.out.println("Monster moves randomly to search for Hero.");
            performMove(null);
        }
    }

    /**
     * 攻擊指定方向的 Hero
     * @param direction Hero 的方向
     */
    private void attackHero(String direction) {
        Crood heroCrood = getCrood().move(direction);
        Hero hero = getGame().getHero();

        if (hero != null && hero.getCrood().equals(heroCrood)) {
            hero.getDamage(50);
        }
    }
}
