package com.example.pokemonshowdown;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME="pokemonDB";
    private static int DB_VERSION=1;

    private static final String POKEMON_TABLE="pokemon";
    private static final String MOVE_TABLE="move";
    private static final String POKEMON_MOVE_TABLE="pokemon_move";

    private static final String NUM_DEX_COL="numDex";
    private static final String NAME_PK_COL="name";
    private static final String TYPE1_COL="type1";
    private static final String TYPE2_COL="type2";
    private static final String HP_COL="hp";
    private static final String DMG_COL="dmg";
    private static final String DEF_COL="def";
    private static final String SPD_COL="spd";
    private static final String IMG_COL="img";
    private static final String IMG_B_COL="imgB";

    private static final String ID_COL="id";
    private static final String NAME_M_COL="name";
    private static final String DMG_M_COL="dmg";
    private static final String HIT_PROB_COL="hit";
    private static final String STATUS_COL="status";
    private static final String STATUS_PROB_COL="statusProb";
    private static final String TYPE_M_COL="type";
    private static final String ATK_ST_COL="atk";
    private static final String RESTORE_HP_PORC_COL="restoreHPProb";
    private static final String PRIORITY_COL="priority";

    private static final String NUM_PK_COL="numPk";
    private static final String ID_M_COL="idMove";

    private ArrayList<Move>moves;
    private ArrayList<Pokemon>pokemons;

    //CONSTRUCTOR
    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        readDB();
    }

    //ONCREATE
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Pokemon table
        String queryTablePokemon="CREATE TABLE "+POKEMON_TABLE+" ("
                +NUM_DEX_COL+" INTEGER PRIMARY KEY, "
                +NAME_PK_COL+" TEXT NOT NULL UNIQUE, "
                +TYPE1_COL+" INTEGER NOT NULL, "
                +TYPE2_COL+" INTEGER, "
                +HP_COL+" INTEGER NOT NULL, "
                +DMG_COL+" INTEGER NOT NULL, "
                +DEF_COL+" INTEGER NOT NULL, "
                +SPD_COL+" INTEGER NOT NULL, "
                +IMG_COL+" INTEGER NOT NULL, "
                +IMG_B_COL+" INTEGER NOT NULL)";

        db.execSQL(queryTablePokemon);

        //Move table
        String queryTableMove="CREATE TABLE "+MOVE_TABLE+" ("
                +ID_COL+" INTEGER PRIMARY KEY, "
                +NAME_M_COL+" TEXT NOT NULL, "
                +DMG_M_COL+" INTEGER NOT NULL, "
                +HIT_PROB_COL+" INTEGER NOT NULL, "
                +STATUS_COL+" INTEGER, "
                +STATUS_PROB_COL+" INTEGER NOT NULL, "
                +TYPE_M_COL+" INTEGER NOT NULL, "
                +RESTORE_HP_PORC_COL+" INTEGER NOT NULL, "
                +PRIORITY_COL+" INTEGER NOT NULL, "
                +ATK_ST_COL+" INTEGER NOT NULL)";

        db.execSQL(queryTableMove);

        //Pokemon_move table
        String queryTablePokemonMove="CREATE TABLE "+POKEMON_MOVE_TABLE+" ("
                +ID_M_COL+" INTEGER NOT NULL REFERENCES "+MOVE_TABLE+", "
                +NUM_PK_COL+" INTEGER NOT NULL REFERENCES "+POKEMON_TABLE+", "
                +"PRIMARY KEY("+ID_M_COL+", "+NUM_PK_COL+"));";

        db.execSQL(queryTablePokemonMove);
    }

    //ONUPDATE
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+POKEMON_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+MOVE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+POKEMON_MOVE_TABLE);
        onCreate(db);
    }
    /**********************************************************************************************/
    private void readDB(){
        this.moves=new ArrayList<Move>();
        this.pokemons=new ArrayList<Pokemon>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+MOVE_TABLE, null);

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String name=cursor.getString(1);
                int damage=cursor.getInt(2);
                int hitProb=cursor.getInt(3);
                int status=cursor.getInt(4);
                int statusProb=cursor.getInt(5);
                int type=cursor.getInt(6);
                int porcRestoreHp=cursor.getInt(7);

                boolean priority;
                if(cursor.getInt(8)==1)priority=true;
                else priority=false;

                boolean atkSt;
                if(cursor.getInt(9)==1)atkSt=true;
                else atkSt=false;

                moves.add(new Move(id, name, damage, hitProb, status, statusProb, type, porcRestoreHp, atkSt, priority));
            }while(cursor.moveToNext());
        }

        Cursor cursor2=db.rawQuery("SELECT * FROM "+POKEMON_TABLE,null);

        if(cursor2.moveToFirst()){
            do{
                int nDex=cursor2.getInt(0);
                String name=cursor2.getString(1);
                int t1=cursor2.getInt(2);
                int t2=cursor2.getInt(3);
                int hp=cursor2.getInt(4);
                int dmg=cursor2.getInt(5);
                int def=cursor2.getInt(6);
                int spd=cursor2.getInt(7);
                int img=cursor2.getInt(8);
                int imgB=cursor2.getInt(9);
                Pokemon pokemon=new Pokemon(nDex, name, t1, t2, hp, dmg, def, spd, img, imgB);
                pokemon.setMoves(getMovesFromPokemon(nDex));

                pokemons.add(pokemon);
            }while(cursor2.moveToNext());
        }

    }

    //it returns an arrayList with the pokemon´s moveset reading from database
    private ArrayList<Move> getMovesFromPokemon(int numDex){
        ArrayList<Move>toret=new ArrayList<Move>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT "+ID_M_COL+ " FROM "+POKEMON_MOVE_TABLE+" WHERE "+NUM_PK_COL+"="+numDex,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                toret.add(moves.get(getPositionOfMove(id)));
            }while(cursor.moveToNext());
        }
        return toret;
    }

    //it returns the position of a move in the moves arrayList having its id
    private int getPositionOfMove(int id){
        for(int i=0;i<moves.size();i++){
            if(moves.get(i).getId()==id)return i;
        }
        return 0;
    }

    /**********************************************************************************************/
    //adds a move to the database
    public void addNewMove(Move move){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put(ID_COL, move.getId());
        values.put(NAME_M_COL, move.getName());
        values.put(DMG_COL, move.getDmg());
        values.put(HIT_PROB_COL, move.getHitProb());
        values.put(STATUS_COL, move.getStatus());
        values.put(STATUS_PROB_COL, move.getStatusProb());
        values.put(TYPE_M_COL, move.getType());
        values.put(RESTORE_HP_PORC_COL, move.getRestoreHPPorc());
        values.put(PRIORITY_COL, move.isPriority());
        values.put(ATK_ST_COL, move.isAtkSt());

        db.insert(MOVE_TABLE, null, values);
        db.close();
    }

    //adds a pokemon and its moves to the database
    public void addNewPokemon(Pokemon pokemon){
        //adding the pokemon at the pokemon table
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put(NUM_DEX_COL, pokemon.getNumDex());
        values.put(NAME_PK_COL, pokemon.getName());
        values.put(TYPE1_COL, pokemon.getType1());
        values.put(TYPE2_COL, pokemon.getType2());
        values.put(HP_COL, pokemon.getHp());
        values.put(DMG_COL, pokemon.getDmg());
        values.put(DEF_COL, pokemon.getDef());
        values.put(SPD_COL, pokemon.getSpd());
        values.put(IMG_COL, pokemon.getImg());
        values.put(IMG_B_COL, pokemon.getImgB());

        db.insert(POKEMON_TABLE, null, values);
        db.close();

        //adding the pokemon´s moveset to the table pokemon_move
        SQLiteDatabase db2=this.getWritableDatabase();
        ContentValues values2 =new ContentValues();

        for(int i=0;i<pokemon.getMoves().size();i++){
            values2.put(NUM_PK_COL, pokemon.getNumDex());
            values2.put(ID_M_COL, pokemon.getMoves().get(i).getId());
        }

        db2.insert(POKEMON_MOVE_TABLE, null, values2);
        db2.close();
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
